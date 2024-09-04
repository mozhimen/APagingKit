package com.mozhimen.pagingk.paging3.data.bases

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.pagingk.paging3.data.commons.IPagingKSource
import com.mozhimen.pagingk.paging3.data.mos.PagingKConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @ClassName BasePagingKSource
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/28
 * @Version 1.0
 */
abstract class BasePagingKSource<RES, DES : Any>(
    private val _pagingKConfig: PagingKConfig
) : PagingSource<Int, DES>(), IPagingKSource<RES, DES>, IUtilK {

    override fun getRefreshKey(state: PagingState<Int, DES>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DES> {
        try {
            val currentPageIndex: Int = params.key ?: _pagingKConfig.pageIndexFirst//页码未定义置为1
            val prevPageIndex = if (currentPageIndex <= _pagingKConfig.pageIndexFirst) null else currentPageIndex - 1
            var nextPageIndex: Int? = null

            //第一次加载
            onLoadStart(currentPageIndex)

            //加载数据
            val pagingKRep = withContext(Dispatchers.IO){
                onLoadRes(currentPageIndex, _pagingKConfig.pageSize)
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
                            _totalPageNum = _totalItemNum / _pagingKConfig.pageSize
                            if (_totalItemNum % _pagingKConfig.pageSize > 0) {
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
                            transformData.add(0, it)
                        }
                        //添加底部
                        if (nextPageIndex == null && onGetFooter() != null) {
                            transformData.add(onGetFooter()!!)
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
                transformData.add(0, it)
            }

            //添加底部
            onGetFooter()?.let {
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