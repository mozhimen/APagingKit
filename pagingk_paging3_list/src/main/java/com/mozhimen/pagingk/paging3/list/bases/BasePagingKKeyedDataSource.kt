package com.mozhimen.pagingk.paging3.list.bases

import androidx.paging.PageKeyedDataSource
import com.mozhimen.pagingk.paging3.list.commons.IPagingKDataSourceLoadListener
import com.mozhimen.pagingk.paging3.list.commons.IPagingKKeyedDataSource
import com.mozhimen.pagingk.basic.mos.PagingKConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName BasePagingKKeyedDataSource
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 16:31
 * @Version 1.0
 */
abstract class BasePagingKKeyedDataSource<RES, DES : Any>(
    private val _pagingKConfig: PagingKConfig,
    private val _coroutineScope: CoroutineScope,
    private val _pagingKDataSourceLoadListener: IPagingKDataSourceLoadListener
) : PageKeyedDataSource<Int, DES>(), IPagingKKeyedDataSource<RES, DES> {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DES>) {
        _coroutineScope.launch(Dispatchers.IO) {
            //回调接口
            _pagingKDataSourceLoadListener.onFirstLoadStart()
            val pagingKRep = onLoadInitial()

            if (pagingKRep.isSuccessful()) {
                val pagingKData = pagingKRep.data
                if (pagingKData != null) {
                    val currentPageItems = pagingKData.currentPageItems
                    if (!currentPageItems.isNullOrEmpty()) {
                        var totalPageNum = pagingKData.totalPageNum
                        if (totalPageNum <= 0) {
                            val totalItemNum = pagingKData.totalItemNum
                            //total 总条数 用总条数/每页数量=总页数
                            totalPageNum = totalItemNum / _pagingKConfig.pageSize
                            if (totalItemNum % _pagingKConfig.pageSize > 0) {
                                totalPageNum += 1
                            }
                        }
                        val adjacentPageKey = if (pagingKData.currentPageIndex >= totalPageNum) null else pagingKData.currentPageIndex + 1
                        val dataAggregation = onDataAggregation(pagingKData.currentPageIndex, currentPageItems)
                        val haveMore = null != adjacentPageKey
                        onAddOtherData(true, haveMore, dataAggregation)
                        //添加头部
                        getHeader()?.let {
                            dataAggregation.add(0, it)
                        }
                        //添加底部
                        if (adjacentPageKey == null && getFooter() != null) {
                            dataAggregation.add(getFooter()!!)
                        }
                        _pagingKDataSourceLoadListener.onFirstLoadFinish(false)
                        callback.onResult(dataAggregation, null, adjacentPageKey)
                        return@launch
                    }
                }
            }
            val dataAggregation: MutableList<DES> = mutableListOf()
            onAddOtherData(isLoadInitial = true, hasMore = false, dataList = dataAggregation)
            //添加头部
            getHeader()?.let {
                dataAggregation.add(0, it)
            }
            //添加底部
            getFooter()?.let {
                dataAggregation.add(it)
            }
            _pagingKDataSourceLoadListener.onFirstLoadFinish(dataAggregation.isEmpty())
            callback.onResult(dataAggregation, null, null)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DES>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DES>) {
        _coroutineScope.launch(Dispatchers.IO) {
            val loadInitial = onLoadAfter(params.key)
            if (loadInitial.isSuccessful()) {
                val pagingKData = loadInitial.data
                if (pagingKData != null) {
                    val currentPageItems = pagingKData.currentPageItems
                    if (!currentPageItems.isNullOrEmpty()) {
                        var totalPageNum = pagingKData.totalPageNum
                        if (totalPageNum <= 0) {
                            val totalItemNum = pagingKData.totalItemNum
                            //total 总条数 用总条数/每页数量=总页数
                            totalPageNum = totalItemNum / _pagingKConfig.pageSize
                            if (totalItemNum % _pagingKConfig.pageSize > 0) {
                                totalPageNum += 1
                            }
                        }
                        val adjacentPageKey = if (pagingKData.currentPageIndex >= totalPageNum) null else pagingKData.currentPageIndex + 1
                        val dataAggregation = onDataAggregation(pagingKData.currentPageIndex, currentPageItems)
                        val haveMore = null != adjacentPageKey
                        onAddOtherData(false, haveMore, dataAggregation)
                        //添加底部
                        if (!haveMore) {
                            getFooter()?.let {
                                dataAggregation.add(it)
                            }
                        }
                        callback.onResult(dataAggregation, adjacentPageKey)
                        return@launch
                    }
                }
            }
            val dataAggregation: MutableList<DES> = mutableListOf()
            onAddOtherData(isLoadInitial = false, hasMore = false, dataList = dataAggregation)
            //添加底部
            getFooter()?.let {
                dataAggregation.add(it)
            }
            callback.onResult(dataAggregation, null)
        }
    }
}