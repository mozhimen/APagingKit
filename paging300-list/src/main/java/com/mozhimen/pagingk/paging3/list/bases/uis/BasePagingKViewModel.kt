package com.mozhimen.pagingk.paging3.list.bases.uis

import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mozhimen.kotlin.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.kotlin.utilk.java.util.UtilKDateWrapper
import com.mozhimen.pagingk.basic.cons.CPagingKLoadState
import com.mozhimen.pagingk.basic.mos.PagingKBaseRes
import com.mozhimen.pagingk.basic.mos.PagingKConfig
import com.mozhimen.pagingk.paging3.list.bases.BasePagingKDataSourceFactory
import com.mozhimen.pagingk.paging3.list.bases.BasePagingKKeyedDataSource
import com.mozhimen.pagingk.paging3.list.commons.IPagingKDataSource
import com.mozhimen.pagingk.paging3.list.commons.IPagingKDataSourceLoadListener
import kotlinx.coroutines.CoroutineScope

/**
 * @ClassName BasePagingKViewModel
 * @Description
 * RES 返回的数据
 * DES 目标数据
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 16:22
 * @Version 1.0
 */
abstract class BasePagingKViewModel<RES, DES : Any>(protected val pagingKConfig: PagingKConfig = PagingKConfig()) : BaseViewModel(), IPagingKDataSource<RES, DES> {
    private val _pagingKDataSourceLoadingListener: IPagingKDataSourceLoadListener = object : IPagingKDataSourceLoadListener {
        override fun onFirstLoadStart() {
            UtilKLogWrapper.d(TAG, "onFirstLoadStart: ${UtilKDateWrapper.getNowStr()}")
            liveLoadState.postValue(CPagingKLoadState.STATE_FIRST_LOAD_START)
        }

        override fun onFirstLoadFinish(isEmpty: Boolean) {
            UtilKLogWrapper.d(TAG, "onFirstLoadFinish: ${UtilKDateWrapper.getNowStr()} isEmpty $isEmpty")
            if (isEmpty)
                liveLoadState.postValue(CPagingKLoadState.STATE_FIRST_LOAD_EMPTY)
            else
                liveLoadState.postValue(CPagingKLoadState.STATE_FIRST_LOAD_FINISH)
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////

    open val livePagedList: LiveData<PagedList<DES>> = generateLivePagedList()
    val liveLoadState = MutableLiveData<Int>()

    ////////////////////////////////////////////////////////////////////////////////////

    fun getViewModelScope(): CoroutineScope =
        viewModelScope

    fun generateLivePagedList(): LiveData<PagedList<DES>> {
        val dataSourceFactory = object : BasePagingKDataSourceFactory<DES>(viewModelScope, _pagingKDataSourceLoadingListener) {
            override fun createPagingKDataSource(coroutineScope: CoroutineScope, pagingKDataSourceLoadingListener: IPagingKDataSourceLoadListener): BasePagingKKeyedDataSource<RES, DES> =
                object : BasePagingKKeyedDataSource<RES, DES>(pagingKConfig, coroutineScope, pagingKDataSourceLoadingListener) {
                    override suspend fun onLoadInitial(): PagingKBaseRes<RES> =
                        this@BasePagingKViewModel.onLoadInitial(pagingKConfig.pageIndexFirst, pagingKConfig.pageSize)

                    override suspend fun onLoadAfter(adjacentPageKey: Int): PagingKBaseRes<RES> =
                        this@BasePagingKViewModel.onLoadAfter(adjacentPageKey, pagingKConfig.pageSize)

                    override suspend fun onDataAggregation(current: Int?, dataList: List<RES>): MutableList<DES> =
                        this@BasePagingKViewModel.onDataAggregation(current, dataList)

                    override suspend fun onAddOtherData(isLoadInitial: Boolean, hasMore: Boolean, dataList: MutableList<DES>) {
                        this@BasePagingKViewModel.onAddOtherData(isLoadInitial, hasMore, dataList)
                    }

                    override fun getHeader(): DES? =
                        this@BasePagingKViewModel.getHeader()

                    override fun getFooter(): DES? =
                        this@BasePagingKViewModel.getFooter()
                }
        }
        return LivePagedListBuilder(
            dataSourceFactory,
            PagedList.Config.Builder()
                .setEnablePlaceholders(false)//设置是否显示占位符
                .setPageSize(pagingKConfig.pageSize)//设置每页的大小
                .setPrefetchDistance(pagingKConfig.pageSize / 2)//设置距离底部还有多少条时开始加载下一页
                .setInitialLoadSizeHint(pagingKConfig.pageSize * 3)//设置首次加载的数量，要求是pageSize的整数倍
                .build()
        ).build()
    }

    ////////////////////////////////////////////////////////////////////////////////////

    open fun onInvalidate() {
        livePagedList.value?.dataSource?.invalidate()
    }

    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * 初次加载
     * @param firstPage 第一页
     * @param pageSize 每页数量
     * @return 请求的数量
     */
    abstract suspend fun onLoadInitial(firstPage: Int, pageSize: Int): PagingKBaseRes<RES>

    /**
     * 加载下一页
     * @param adjacentPageKey 当前页数
     * @param pageSize 每页数量
     * @return 请求的数量
     */
    abstract suspend fun onLoadAfter(adjacentPageKey: Int, pageSize: Int): PagingKBaseRes<RES>
}