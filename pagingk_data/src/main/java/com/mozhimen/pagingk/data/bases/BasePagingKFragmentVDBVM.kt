package com.mozhimen.pagingk.data.bases

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.PagedList
import com.mozhimen.basick.elemk.androidx.fragment.bases.databinding.BaseFragmentVDB
import com.mozhimen.basick.utilk.android.view.applyGone
import com.mozhimen.basick.utilk.android.view.applyVisible
import com.mozhimen.pagingk.data.commons.IPagingKActivity
import com.mozhimen.pagingk.data.cons.CPagingKLoadingState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @ClassName BasePagingKFragmentVBVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/16 15:05
 * @Version 1.0
 */
abstract class BasePagingKFragmentVDBVM<DES : Any, VB : ViewDataBinding, VM : BasePagingKViewModel<*, DES>> : BaseFragmentVDB<VB>(), IPagingKActivity<DES, VM> {

    @CallSuper
    override fun initLayout() {
        super.initLayout()
        getSwipeRefreshLayout()?.apply {
            if (getSwipeRefreshLayoutColorScheme() != 0)
                setColorSchemeResources(getSwipeRefreshLayoutColorScheme())
            setOnRefreshListener { onRefresh() }
        }
        getRecyclerView().apply {
            layoutManager = getRecyclerViewLayoutManager()
            getRecyclerViewItemDecoration()?.let { addItemDecoration(it) }
            adapter = if (getLoadStateAdapter() != null)
                getPagingDataAdapter().withLoadStateFooter(getLoadStateAdapter()!!)
            else
                getPagingDataAdapter()
        }
        getViewModel().liveLoadState.observe(this) {
            when (it) {
                CPagingKLoadingState.STATE_FIRST_LOAD_START -> {
                    getSwipeRefreshLayout()?.isRefreshing = true
                    onFirstLoadStart()
                }

                CPagingKLoadingState.STATE_FIRST_LOAD_FINISH -> {
                    getSwipeRefreshLayout()?.isRefreshing = false
                    onFirstLoadFinish()
                }

                else -> {
                    getSwipeRefreshLayout()?.isRefreshing = false
                    onFirstLoadEmpty()
                }
            }
        }

        getViewModel().pager.flow
            .onEach { getPagingDataAdapter().submitData(it) }
            .flowWithLifecycle(this.lifecycle, Lifecycle.State.CREATED)
            .launchIn(getViewModel().getViewModelScope())
    }

    override fun onRefresh() {
        getPagingDataAdapter().refresh()
    }

    override fun onFirstLoadEmpty() {
        getRecyclerView().applyGone()
    }

    override fun onFirstLoadFinish() {
        getRecyclerView().applyVisible()
    }
}