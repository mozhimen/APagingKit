package com.mozhimen.pagingk.paging3.compose

import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mozhimen.kotlin.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.kotlin.utilk.java.util.UtilKDateWrapper
import com.mozhimen.pagingk.basic.commons.IPagingKSource
import com.mozhimen.pagingk.basic.cons.CPagingKLoadingState
import com.mozhimen.pagingk.basic.mos.PagingKBaseRes
import com.mozhimen.pagingk.basic.mos.PagingKConfig
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @ClassName BasePagingKViewModel
 * @Description
 * RES 返回的数据
 * DES 目标数据
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 16:22
 * @Version 1.0
 */
abstract class BasePagingKViewModel<RES, DES : Any> constructor(protected val pagingKConfig: PagingKConfig = PagingKConfig()) : BaseViewModel(), IPagingKSource<RES, DES> {

    @OptIn(androidx.paging.ExperimentalPagingApi::class)
    val pager by lazy {
        Pager(
            config = PagingConfig(
                pageSize = pagingKConfig.pageSize,//设置每页的大小
                prefetchDistance = pagingKConfig.prefetchDistance,//设置距离底部还有多少条时开始加载下一页
                enablePlaceholders = false,//设置是否显示占位符
                initialLoadSize = pagingKConfig.initialLoadSize//设置首次加载的数量，要求是pageSize的整数倍
            ),
            initialKey = pagingKConfig.pageIndexFirst,
            pagingSourceFactory = {
                object : BasePagingKSource<RES, DES>(pagingKConfig) {
                    override suspend fun onLoadStart(currentPageIndex: Int) {
                        this@BasePagingKViewModel.onLoadStart(currentPageIndex)
                    }

                    override suspend fun onLoadRes(currentPageIndex: Int, pageSize: Int): PagingKBaseRes<RES> {
                        return this@BasePagingKViewModel.onLoadRes(currentPageIndex, pageSize)
                    }

                    override suspend fun onLoadFinished(currentPageIndex: Int, isResEmpty: Boolean) {
                        this@BasePagingKViewModel.onLoadFinished(currentPageIndex, isResEmpty)
                    }

                    override suspend fun onTransformData(currentPageIndex: Int?, datas: List<RES>): List<DES> {
                        return this@BasePagingKViewModel.onTransformData(currentPageIndex, datas)
                    }

                    override suspend fun onCombineData(currentPageIndex: Int?, datas: MutableList<DES>) {
                        this@BasePagingKViewModel.onCombineData(currentPageIndex, datas)
                    }

                    override suspend fun onGetHeader(): DES? {
                        return this@BasePagingKViewModel.onGetHeader()
                    }

                    override suspend fun onGetFooter(): DES? {
                        return this@BasePagingKViewModel.onGetFooter()
                    }
                }
            }
        )
    }

    private val _flowLoadState = MutableStateFlow<Int?>(null)
    val flowLoadState get() = _flowLoadState
    private val _flowPagingData = pager.flow
    val flowPagingData get() = _flowPagingData

    ////////////////////////////////////////////////////////////////////////////////////

    override suspend fun onLoadStart(currentPageIndex: Int) {
        if (currentPageIndex == pagingKConfig.pageIndexFirst) {
            UtilKLogWrapper.d(TAG, "onFirstLoadStart: ${UtilKDateWrapper.getNowStr()}")
            _flowLoadState.value = (CPagingKLoadingState.STATE_FIRST_LOAD_START)
        }
    }

    override suspend fun onLoadFinished(currentPageIndex: Int, isResEmpty: Boolean) {
        if (currentPageIndex == pagingKConfig.pageIndexFirst) {
            UtilKLogWrapper.d(TAG, "onFirstLoadFinish: ${UtilKDateWrapper.getNowStr()} isEmpty $isResEmpty")
            if (isResEmpty)
                _flowLoadState.value = (CPagingKLoadingState.STATE_FIRST_LOAD_EMPTY)
            else
                _flowLoadState.value = (CPagingKLoadingState.STATE_FIRST_LOAD_FINISH)
        }
    }
}