package com.mozhimen.pagingk.paging3.data.bases

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.mozhimen.basick.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.pagingk.paging3.data.commons.IPagingKActivity
import com.mozhimen.pagingk.paging3.data.cons.CPagingKLoadingState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @ClassName BasePagingKProxy
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/5/25 22:21
 * @Version 1.0
 */
@OApiInit_ByLazy
@OApiCall_BindLifecycle
class BasePagingKProxy<DES : Any, VM : BasePagingKViewModel<*, DES>>(private var _pagingKActivity: IPagingKActivity<DES, VM>?) : BaseWakeBefDestroyLifecycleObserver() {

    fun initLayout(owner: LifecycleOwner) {
        val pagingKActivity = _pagingKActivity ?: return
        pagingKActivity.getSwipeRefreshLayout()?.apply {
            if (pagingKActivity.getSwipeRefreshLayoutColorScheme() != 0)
                setColorSchemeResources(pagingKActivity.getSwipeRefreshLayoutColorScheme())
            setOnRefreshListener { pagingKActivity.onRefresh() }
        }
        pagingKActivity.getRecyclerView().apply {
            layoutManager = pagingKActivity.getRecyclerViewLayoutManager()
            pagingKActivity.getRecyclerViewItemDecoration()?.let { addItemDecoration(it) }
            adapter = if (pagingKActivity.getLoadStateAdapter() != null)
                pagingKActivity.getPagingDataAdapter().withLoadStateFooter(pagingKActivity.getLoadStateAdapter()!!)
            else
                pagingKActivity.getPagingDataAdapter()
        }
        pagingKActivity.getViewModel().liveLoadState.observe(owner) {
            when (it) {
                CPagingKLoadingState.STATE_FIRST_LOAD_START -> {
                    pagingKActivity.getSwipeRefreshLayout()?.isRefreshing = true
                    pagingKActivity.onFirstLoadStart()
                }

                CPagingKLoadingState.STATE_FIRST_LOAD_FINISH -> {
                    pagingKActivity.getSwipeRefreshLayout()?.isRefreshing = false
                    pagingKActivity.onFirstLoadFinish()
                }

                else -> {
                    pagingKActivity.getSwipeRefreshLayout()?.isRefreshing = false
                    pagingKActivity.onFirstLoadEmpty()
                }
            }
        }

        pagingKActivity.getViewModel().pager.flow
            .onEach { pagingKActivity.getPagingDataAdapter().submitData(it) }
            .flowWithLifecycle(owner.lifecycle, Lifecycle.State.CREATED)
            .launchIn(pagingKActivity.getViewModel().getViewModelScope())
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _pagingKActivity = null
        super.onDestroy(owner)
    }
}