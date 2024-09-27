package com.mozhimen.pagingk.paging3.compose

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.RoomDatabase
import com.mozhimen.kotlin.elemk.commons.IHasId
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.pagingk.basic.commons.IPagingKDataSource
import com.mozhimen.pagingk.basic.commons.IPagingKRemoteMediatorDbDao
import com.mozhimen.pagingk.basic.commons.IPagingKStateSource
import com.mozhimen.pagingk.basic.cons.SPageState
import com.mozhimen.pagingk.basic.db.KeyDb
import com.mozhimen.pagingk.basic.db.KeyEntity
import com.mozhimen.pagingk.basic.mos.PagingKBaseRes
import com.mozhimen.pagingk.basic.mos.PagingKConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @ClassName BasePagingKRemoteMediator
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/20
 * @Version 1.0
 */
@OptIn(ExperimentalPagingApi::class)
abstract class BasePagingKRemoteMediator<RES, DES : IHasId> : RemoteMediator<Int, DES>(),
    IPagingKStateSource<RES, DES>,
    IPagingKDataSource<RES, DES>,
    IPagingKRemoteMediatorDbDao<DES>,
    IUtilK {

    abstract val pagingKConfig: PagingKConfig

    abstract fun getDb(): RoomDatabase

    ////////////////////////////////////////////////////////////////////////////////////

    override suspend fun load(loadType: LoadType, state: PagingState<Int, DES>): MediatorResult {
        try {
            /**
             * 在这个方法内将会做三件事
             *
             * 1. 参数 LoadType 有个三个值，关于这三个值如何进行判断
             *      LoadType.REFRESH
             *      LoadType.PREPEND
             *      LoadType.APPEND
             *
             * 2. 访问网络数据
             *
             * 3. 将网路插入到本地数据库中
             */
            // 第一步： 判断 LoadType
            val pageState = getPage(
                loadType = loadType,
                state = state
            )
            val currentPageIndexTemp = when (pageState) {
                is SPageState.Append -> pageState.page ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )

                is SPageState.Prepend -> pageState.page ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                is SPageState.Refresh -> pageState.page
            }
//            val currentPageIndexTemp = when (loadType) {
//                LoadType.REFRESH -> pagingKConfig.pageIndexFirst// 首次访问 或者调用 PagingDataAdapter.refresh()
//
//                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)// 在当前加载的数据集的开头加载数据时
//
//                LoadType.APPEND -> { // 下来加载更多时触发
//                    /**
//                     *
//                     * 这里主要获取下一页数据的开始位置，可以理解为从什么地方开始加载下一页数据
//                     * 这里有两种方式来获取下一页加载数据的位置
//                     * 方式一：这种方式比较简单，当前页面最后一条数据是下一页的开始位置
//                     * 方式二：比较麻烦，当前分页数据没有对应的远程 key，这个时候需要我们自己建表,
//                     */
//
//                    /**
//                     * 方式一：这种方式比较简单，当前页面最后一条数据是下一页的开始位置
//                     * 通过 load 方法的参数 state 获取当页面最后一条数据
//                     */
//                    Log.d(TAG, "load: state.pages ${state.pages} state.lastItemOrNull() ${state.lastItemOrNull()}")
//                    if (state.pages.isEmpty()) {
//                        pagingKConfig.pageIndexFirst.also { Log.d(TAG, "load: state.pages.isEmpty() $it") }
//                    } else {
//                        (state.pages.size + pagingKConfig.pageIndexFirst) //?: return MediatorResult.Success(endOfPaginationReached = true)
//                    }
//
//                    /**
//                     * 方式二：比较麻烦，当前分页数据没有对应的远程 key，这个时候需要我们自己建表
//                     */
//                    val remoteKey = db.withTransaction {
//                        db.remoteKeysDao().getRemoteKeys(remotePokemon)
//                    }
//                    if (remoteKey == null || remoteKey.nextKey == null) {
//                        return MediatorResult.Success(endOfPaginationReached = true)
//                    }
//                    remoteKey.nextKey
//                }
//            }

            // 第二步： 请问网络分页数据
            val currPageIndex = currentPageIndexTemp//页码未定义置为1
            Log.d(TAG, "load: currentPageIndex $currPageIndex")
            val prevPageIndex: Int? = if (currPageIndex <= pagingKConfig.pageIndexFirst) null else currPageIndex - 1
            var nextPageIndex: Int? = null

            //加载之前
            onLoadStart(currPageIndex)

            //加载数据
            val pagingKBaseRes: PagingKBaseRes<RES> = withContext(Dispatchers.IO) {
                onLoading(currPageIndex, pagingKConfig.pageSize)
            }
            val transformData: MutableList<DES>
            var endOfPagination = true

            if (pagingKBaseRes.isSuccessful()) {
                val _data = pagingKBaseRes.data
                if (_data != null) {
                    val _currentPageItems = _data.currentPageItems
                    if (!_currentPageItems.isNullOrEmpty()) {
                        var _totalPageNum = _data.totalPageNum
                        if (_totalPageNum <= 0) {
                            val _totalItemNum = _data.totalItemNum
                            //total 总条数 用总条数/每页数量=总页数
                            _totalPageNum = _totalItemNum / pagingKConfig.pageSize
                            if (_totalItemNum % pagingKConfig.pageSize > 0) {
                                _totalPageNum += 1
                            }
                        }
                        nextPageIndex = if (currPageIndex >= _totalPageNum) null else currPageIndex + 1

                        //加载基础数据
                        transformData = onTransformData(currPageIndex, _currentPageItems).toMutableList()

                        //加载结束
                        onLoadFinished(currPageIndex, false)

                        //是否是最后一页
                        endOfPagination = transformData.isEmpty()

                        // 第三步： 插入数据库(事务)
                        try {
                            getDb().beginTransaction()
                            KeyDb.get().beginTransaction()

                            if (loadType == LoadType.REFRESH) {
                                Log.d(TAG, "refreshDb: ")
                                deleteAll_ofDb()
                                KeyDb.getKeyDao().deleteAll()
                            }

                            insertAll_ofDb(transformData.toList())
                            val keys = transformData.map { KeyEntity(id = it.id, prevPageIndex, currPageIndex, nextPageIndex) }
                            KeyDb.getKeyDao().insertKeys(keys)

                            getDb().setTransactionSuccessful()
                            KeyDb.get().setTransactionSuccessful()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            getDb().endTransaction()
                            KeyDb.get().endTransaction()
                        }

                        Log.d(TAG, "load: currPageIndex $currPageIndex prevPageIndex $prevPageIndex nextPageIndex $nextPageIndex")
                        return MediatorResult.Success(endOfPagination)
                    }
                }
            }

            //加载结束
            onLoadFinished(currPageIndex, endOfPagination)

            return MediatorResult.Success(endOfPagination)
        } catch (e: Exception) {
            UtilKLogWrapper.e(TAG, e.message ?: "")
            return MediatorResult.Error(throwable = e)
        }
    }

    private suspend fun getPage(
        loadType: LoadType,
        state: PagingState<Int, DES>,
    ): SPageState {

        return when (loadType) {
            // loading
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                val page = remoteKeys?.nextPage?.minus(1) ?: pagingKConfig.pageIndexFirst
                SPageState.Refresh(page = page)
            }

            // has data, load more
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val page = remoteKeys?.nextPage
                SPageState.Append(page = page)
            }

            // has data, load previous
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val page = remoteKeys?.prevPage
                SPageState.Prepend(page = page)
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, DES>): KeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                KeyDb.getKeyDao().getKey(id)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, DES>): KeyEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { id ->
                KeyDb.getKeyDao().getKey(id.id)
            }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, DES>): KeyEntity? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { id ->
                KeyDb.getKeyDao().getKey(id.id)
            }

    }
}