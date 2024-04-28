package com.mozhimen.pagingk.data.bases

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.basick.utilk.java.util.UtilKDateWrapper
import com.mozhimen.pagingk.data.commons.IPagingKSource
import com.mozhimen.pagingk.data.cons.CPagingKLoadingState
import com.mozhimen.pagingk.data.mos.PagingKBaseRes
import com.mozhimen.pagingk.data.mos.PagingKConfig
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
abstract class BasePagingKViewModel<RES, DES : Any>(protected val pagingKConfig: PagingKConfig = PagingKConfig()) : BaseViewModel(), IPagingKSource<RES, DES> {

    open val pageSource by lazy {
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

    @OptIn(androidx.paging.ExperimentalPagingApi::class)
    open val pager by lazy {
        Pager(
            config = PagingConfig(
                pageSize = pagingKConfig.pageSize,//设置每页的大小
                prefetchDistance = pagingKConfig.pageSize / 2,//设置距离底部还有多少条时开始加载下一页
                enablePlaceholders = false,//设置是否显示占位符
                initialLoadSize = pagingKConfig.pageSize * 3//设置首次加载的数量，要求是pageSize的整数倍
            ),
            initialKey = pagingKConfig.pageIndexFirst,
            pagingSourceFactory = { pageSource }
        )
    }
    val liveLoadState = MutableLiveData<Int>()

    ////////////////////////////////////////////////////////////////////////////////////

    fun getViewModelScope(): CoroutineScope =
        viewModelScope

    ////////////////////////////////////////////////////////////////////////////////////

    open fun onInvalidate() {
        pageSource.invalidate()
    }

    ////////////////////////////////////////////////////////////////////////////////////

    override suspend fun onLoadStart(currentPageIndex: Int) {
        if (currentPageIndex == pagingKConfig.pageIndexFirst) {
            UtilKLogWrapper.d(TAG, "onFirstLoadStart: ${UtilKDateWrapper.getNowStr()}")
            liveLoadState.postValue(CPagingKLoadingState.STATE_FIRST_LOAD_START)
        }
    }

    override suspend fun onLoadFinished(currentPageIndex: Int, isResEmpty: Boolean) {
        if (currentPageIndex == pagingKConfig.pageIndexFirst) {
            UtilKLogWrapper.d(TAG, "onFirstLoadFinish: ${UtilKDateWrapper.getNowStr()} isEmpty $isResEmpty")
            if (isResEmpty)
                liveLoadState.postValue(CPagingKLoadingState.STATE_FIRST_LOAD_EMPTY)
            else
                liveLoadState.postValue(CPagingKLoadingState.STATE_FIRST_LOAD_FINISH)
        }
    }
}