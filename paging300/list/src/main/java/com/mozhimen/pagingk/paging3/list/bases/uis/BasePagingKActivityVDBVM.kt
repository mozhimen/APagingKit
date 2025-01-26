package com.mozhimen.pagingk.paging3.list.bases.uis

import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.mozhimen.bindk.bases.activity.databinding.BaseActivityVDB
import com.mozhimen.kotlin.utilk.android.view.applyGone
import com.mozhimen.kotlin.utilk.android.view.applyVisible
import com.mozhimen.pagingk.basic.cons.CPagingKLoadState
import com.mozhimen.pagingk.paging3.list.commons.IPagingKActivity

/**
 * @ClassName BasePagingKActivityVBVM
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/26 22:51
 * @Version 1.0
 */
abstract class BasePagingKActivityVDBVM<DES : Any, VB : ViewDataBinding, VM : BasePagingKViewModel<*, DES>> : BaseActivityVDB<VB>(), IPagingKActivity<DES, VM> {

    private val _pagedListObserver: Observer<PagedList<DES>> by lazy {
        Observer<PagedList<DES>> { pagedList ->
            UtilKLogWrapper.d(TAG, "_pagedListObserver_: pagedList $pagedList")
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
            adapter = if (getLoadStateAdapter() != null) {
                getPagedListAdapter().withLoadStateFooter(getLoadStateAdapter()!!)
            }else
                getPagedListAdapter()
        }
        getViewModel().liveLoadState.observe(this) {
            when (it) {
                CPagingKLoadState.STATE_FIRST_LOAD_START -> {
                    getSwipeRefreshLayout()?.isRefreshing = true
                    onLoadStart()
                }
                CPagingKLoadState.STATE_FIRST_LOAD_FINISH -> {
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