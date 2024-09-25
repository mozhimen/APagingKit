package com.mozhimen.pagingk.paging3.compose

import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mozhimen.kotlin.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.kotlin.utilk.java.util.UtilKDateWrapper
import com.mozhimen.pagingk.basic.commons.IPagingKStateSource
import com.mozhimen.pagingk.basic.cons.CPagingKLoadState
import com.mozhimen.pagingk.basic.mos.PagingKBaseRes
import com.mozhimen.pagingk.basic.mos.PagingKConfig
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import androidx.paging.cachedIn
import com.mozhimen.pagingk.basic.commons.IPagingKDataSource

/**
 * @ClassName BasePagingKViewModel
 * @Description
 * RES 返回的数据
 * DES 目标数据
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 16:22
 * @Version 1.0
 */
abstract class BasePagingKViewModel<RES, DES : Any>(protected val pagingKConfig: PagingKConfig = PagingKConfig()) : BaseViewModel(), IPagingKStateSource<RES, DES> {

    val flowLoadState = MutableStateFlow<Int?>(null)
    val flowPagingData = getPager().flow.cachedIn(viewModelScope)

    ////////////////////////////////////////////////////////////////////////////////////

    abstract val dataSource: IPagingKDataSource<RES, DES>?

    open fun getPagingSourceFactory(): () -> PagingSource<Int, DES> {
        return {
            object : BasePagingKPagingSource<RES, DES>() {
                override val pagingKConfig: PagingKConfig
                    get() = this@BasePagingKViewModel.pagingKConfig

                override suspend fun onLoadStart(currentPageIndex: Int) {
                    this@BasePagingKViewModel.onLoadStart(currentPageIndex)
                }

                override suspend fun onLoading(currentPageIndex: Int, pageSize: Int): PagingKBaseRes<RES> {
                    return this@BasePagingKViewModel.onLoading(currentPageIndex, pageSize)
                }

                override suspend fun onLoadFinished(currentPageIndex: Int, isResEmpty: Boolean) {
                    this@BasePagingKViewModel.onLoadFinished(currentPageIndex, isResEmpty)
                }

                override suspend fun onTransformData(currentPageIndex: Int?, datas: List<RES>): List<DES> {
                    return dataSource?.onTransformData(currentPageIndex, datas) ?: emptyList()
                }

                override suspend fun onCombineData(currentPageIndex: Int?, datas: MutableList<DES>) {
                    dataSource?.onCombineData(currentPageIndex, datas)
                }

                override suspend fun onGetHeader(): DES? {
                    return dataSource?.onGetHeader()
                }

                override suspend fun onGetFooter(): DES? {
                    return dataSource?.onGetFooter()
                }
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    open fun getRemoteMediator(): RemoteMediator<Int, DES>? = null

    @OptIn(ExperimentalPagingApi::class)
    open fun getPager(): Pager<Int, DES> =
        Pager(
            config = PagingConfig(
                pageSize = pagingKConfig.pageSize,//设置每页的大小
                prefetchDistance = pagingKConfig.prefetchDistance,//设置距离底部还有多少条时开始加载下一页
                enablePlaceholders = pagingKConfig.enablePlaceholders,//设置是否显示占位符
                initialLoadSize = pagingKConfig.initialLoadSize//设置首次加载的数量，要求是pageSize的整数倍
            ),
            initialKey = pagingKConfig.pageIndexFirst,
            pagingSourceFactory = getPagingSourceFactory(),
            remoteMediator = getRemoteMediator()
        )

    ////////////////////////////////////////////////////////////////////////////////////

    override suspend fun onLoadStart(currentPageIndex: Int) {
        if (currentPageIndex == pagingKConfig.pageIndexFirst) {
            UtilKLogWrapper.d(TAG, "onFirstLoadStart: ${UtilKDateWrapper.getNowStr()}")
            flowLoadState.value = (CPagingKLoadState.STATE_FIRST_LOAD_START)
        }
    }

    override suspend fun onLoadFinished(currentPageIndex: Int, isResEmpty: Boolean) {
        if (currentPageIndex == pagingKConfig.pageIndexFirst) {
            UtilKLogWrapper.d(TAG, "onFirstLoadFinish: ${UtilKDateWrapper.getNowStr()} isEmpty $isResEmpty")
            if (isResEmpty)
                flowLoadState.value = (CPagingKLoadState.STATE_FIRST_LOAD_EMPTY)
            else
                flowLoadState.value = (CPagingKLoadState.STATE_FIRST_LOAD_FINISH)
        }
    }
}