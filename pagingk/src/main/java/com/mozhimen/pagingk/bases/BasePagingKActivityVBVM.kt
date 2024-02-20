package com.mozhimen.pagingk.bases

import android.util.Log
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.view.applyGone
import com.mozhimen.basick.utilk.android.view.applyVisible
import com.mozhimen.pagingk.commons.IPagingKActivity
import com.mozhimen.pagingk.cons.CPagingKLoadingState

/**
 * @ClassName BasePagingKActivityVBVM
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/26 22:51
 * @Version 1.0
 */
abstract class BasePagingKActivityVBVM<DES : Any, VB : ViewDataBinding, VM : BasePagingKViewModel<*, DES>> : BaseActivityVB<VB>(), IPagingKActivity<DES, VM> {

    private val _pagedListObserver: Observer<PagedList<DES>> by lazy {
        Observer<PagedList<DES>> { pagedList ->
            Log.d(TAG, "_pagedListObserver_: pagedList $pagedList")
            getPagedListAdapter().submitList(pagedList)
        }
    }

    //////////////////////////////////////////////////////////////////////////////

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
                getPagedListAdapter()/*.withLoadStateFooter(getLoadStateAdapter()!!)*/
            else
                getPagedListAdapter()
        }
        getViewModel().liveLoadState.observe(this) {
            when (it) {
                CPagingKLoadingState.STATE_FIRST_LOAD_START -> {
                    getSwipeRefreshLayout()?.isRefreshing = true
                    onLoadStart()
                }
                CPagingKLoadingState.STATE_FIRST_LOAD_COMPLETED -> {
                    getSwipeRefreshLayout()?.isRefreshing = false
                    onLoadComplete()
                }
                else -> {
                    getSwipeRefreshLayout()?.isRefreshing = false
                    onLoadEmpty()
                }
            }
        }
    }

    @CallSuper
    override fun onRefresh() {
        getViewModel().onInvalidate()
    }

    @CallSuper
    override fun onLoadEmpty() {
        getRecyclerView().applyGone()
    }

    @CallSuper
    override fun onLoadComplete() {
        getRecyclerView().applyVisible()
    }

    override fun onResume() {
        super.onResume()
        getViewModel().livePagedList.observe(this, _pagedListObserver)
    }
}