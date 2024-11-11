package com.mozhimen.pagingk.paging3.data.bases

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.pagingk.basic.commons.IPagingKDataSource
import com.mozhimen.pagingk.basic.commons.IPagingKStateSource
import com.mozhimen.pagingk.basic.mos.PagingKConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @ClassName BasePagingKSource
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/28
 * @Version 1.0
 */
abstract class BasePagingKPagingSource<RES, DES : Any> : PagingSource<Int, DES>(),
    IPagingKStateSource<RES, DES>,
    IPagingKDataSource<RES, DES>,
    IUtilK {

    abstract val pagingKConfig: PagingKConfig

    ////////////////////////////////////////////////////////////////////////////////////

    override fun getRefreshKey(state: PagingState<Int, DES>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DES> {
        try {
            val currentPageIndex: Int = params.key ?: pagingKConfig.pageIndexFirst//页码未定义置为1
            val prevPageIndex = if (currentPageIndex <= pagingKConfig.pageIndexFirst) null else currentPageIndex - 1
            var nextPageIndex: Int? = null

            //第一次加载
            onLoadStart(currentPageIndex)

            //加载数据
            val pagingKRep = withContext(Dispatchers.IO){
                onLoading(currentPageIndex, pagingKConfig.pageSize)
            }
            val transformData: MutableList<DES>

            if (pagingKRep.isSuccessful()) {
                val _data = pagingKRep.data
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
                        nextPageIndex = if (currentPageIndex >= _totalPageNum) null else currentPageIndex + 1

                        //加载基础数据
                        transformData = onTransformData(currentPageIndex, _currentPageItems).toMutableList()

                        //组合数据
                        onCombineData(currentPageIndex, transformData)

                        //添加头部
                        onGetHeader()?.let {
                            if (currentPageIndex == pagingKConfig.pageIndexFirst) {
                                transformData.add(0, it)
                            }
                        }

                        //添加底部
                        onGetFooter()?.let {
                            Log.d(TAG, "load: onGetFooter $it")
                            if (nextPageIndex == null) {
                                transformData.add(it)
                            }
                        }

                        //第一次加载结束
                        onLoadFinished(currentPageIndex, false)

                        return LoadResult.Page(transformData, prevPageIndex, nextPageIndex)
                    }
                }
            }

            transformData = mutableListOf()

            //组合数据
            onCombineData(currentPageIndex, transformData)

            //添加头部
            onGetHeader()?.let {
                if (currentPageIndex == pagingKConfig.pageIndexFirst) {
                    transformData.add(0, it)
                }
            }

            //添加底部
            onGetFooter()?.let {
                Log.d(TAG, "load: onGetFooter1 $it")
                transformData.add(it)
            }

            //第一次加载结束
            onLoadFinished(currentPageIndex, true)

            return LoadResult.Page(transformData, prevPageIndex, nextPageIndex)
        } catch (e: Exception) {
            UtilKLogWrapper.e(TAG, e.message ?: "")
            return LoadResult.Error(throwable = e)
        }
    }
}