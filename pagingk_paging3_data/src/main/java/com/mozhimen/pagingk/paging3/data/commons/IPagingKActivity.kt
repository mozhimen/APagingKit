package com.mozhimen.pagingk.paging3.data.commons

import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mozhimen.pagingk.paging3.data.bases.BasePagingKDataAdapter
import com.mozhimen.pagingk.paging3.data.bases.uis.BasePagingKViewModel

/**
 * @ClassName IPagingKFragment
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/17 17:13
 * @Version 1.0
 */
interface IPagingKActivity<DES : Any, VM : BasePagingKViewModel<*, DES>> {
    fun getViewModel(): VM
    fun getPagingDataAdapter(): BasePagingKDataAdapter<DES, *>
    fun getLoadStateAdapter(): LoadStateAdapter<*>? = null
    fun getSwipeRefreshLayout(): SwipeRefreshLayout?
    fun getSwipeRefreshLayoutColorScheme(): Int = 0
    fun getRecyclerView(): RecyclerView
    fun getRecyclerViewLayoutManager(): LayoutManager
    fun getRecyclerViewItemDecoration(): ItemDecoration? = null
    fun onRefresh()
    fun onFirstLoadStart()
    fun onFirstLoadEmpty()
    fun onFirstLoadFinish()
}