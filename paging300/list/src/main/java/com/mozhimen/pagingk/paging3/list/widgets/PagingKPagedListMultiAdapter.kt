package com.mozhimen.pagingk.paging3.list.widgets

import android.annotation.SuppressLint
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.kotlin.utilk.android.view.applyDebounceClickListener
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.pagingk.paging3.list.bases.uis.BasePagingKVHKProvider
import com.mozhimen.xmlk.vhk.VHKRecycler

/**
 * @ClassName PagingKPagedListMultiAdapter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 11:44
 * @Version 1.0
 */
open class PagingKPagedListMultiAdapter<DATA : Any>(itemCallback: ItemCallback<DATA>) : PagingKPagedListAdapter<DATA>(0, itemCallback), IUtilK {
    private val _pagingKItems by lazy(LazyThreadSafetyMode.NONE) { SparseArray<BasePagingKVHKProvider<DATA>>() }

    /////////////////////////////////////////////////////////////////////////////////

    /**Fe
     * 通过 ViewType 获取 BaseItemProvider
     * 例如：如果ViewType经过特殊处理，可以重写此方法，获取正确的Provider
     * （比如 ViewType 通过位运算进行的组合的）
     */
    fun getPagingKVHKProvider(viewType: Int): BasePagingKVHKProvider<DATA>? =
        _pagingKItems.get(viewType)

    /**
     * 必须通过此方法，添加 provider
     */
    fun addPagingKVHKProvider(item: BasePagingKVHKProvider<DATA>) {
        item.setAdapter(this)
        _pagingKItems.put(item.itemViewType, item)
    }

    /////////////////////////////////////////////////////////////////////////////////

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHKRecycler {
        val recyclerKPageItem = getPagingKVHKProvider(viewType)
        checkNotNull(recyclerKPageItem) { "ViewType: $viewType no such provider found，please use addItemProvider() first!" }
        recyclerKPageItem.context = parent.context
        return recyclerKPageItem.onCreateViewHolder(parent, viewType).apply {
            recyclerKPageItem.onViewHolderCreated(this, viewType)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBindViewHolderInner(holder: VHKRecycler, item: DATA?, position: Int) {
        UtilKLogWrapper.d(TAG, "onBindViewHolderInner: holder $holder item $holder position $holder")
        getPagingKVHKProvider(holder.itemViewType)?.onBindViewHolder(holder, item, position)
    }

    override fun onBindViewHolderInner(holder: VHKRecycler, item: DATA?, position: Int, payloads: List<Any>) {
        UtilKLogWrapper.d(TAG, "onBindViewHolderInner: holder $holder item $holder position $holder payloads $payloads")
        getPagingKVHKProvider(holder.itemViewType)?.onBindViewHolder(holder, item, position, payloads)
    }

    /////////////////////////////////////////////////////////////////////////////////

    override fun onViewAttachedToWindow(holder: VHKRecycler) {
        val position = getPosition(holder)
        getPagingKVHKProvider(holder.itemViewType)?.onViewAttachedToWindow(holder, position?.let { getItem(it) }, position)
    }

    override fun onViewDetachedFromWindow(holder: VHKRecycler) {
        val position = getPosition(holder)
        getPagingKVHKProvider(holder.itemViewType)?.onViewDetachedFromWindow(holder, position?.let { getItem(it) }, position)
    }

    override fun onViewRecycled(holder: VHKRecycler) {
        val position = getPosition(holder)
        getPagingKVHKProvider(holder.itemViewType)?.onViewRecycled(holder, position?.let { getItem(it) }, position)
    }

    /////////////////////////////////////////////////////////////////////////////////

    //    override fun bindViewClickListener(holder: VHKRecycler, viewType: Int, position: Int) {
//        super.bindViewClickListener(holder, viewType, position)
//        bindItemViewClickListener(holder, viewType, position)
//        bindItemChildViewClickListener(holder, viewType, position)
//    }

    protected open fun bindItemViewClickListener(holder: VHKRecycler, viewType: Int, position: Int) {
        if (getOnItemClickListener() == null) {
            //如果没有设置点击监听，则回调给 itemProvider
            //Callback to itemProvider if no click listener is set
            holder.itemView.applyDebounceClickListener(lifecycleScope, 1000) {
//                val position = holder.adapterPosition
                if (position == RecyclerView.NO_POSITION)
                    return@applyDebounceClickListener
//                val itemViewType = holder.itemViewType
//                if (itemViewType == -0x201) //过滤掉暂无更多
//                    return@applyDebounceClickListener
                val recyclerKPageItem = _pagingKItems.get(viewType)
                recyclerKPageItem.onClick(holder, it, getItem(position), position)
            }
        }
    }

    protected open fun bindItemChildViewClickListener(holder: VHKRecycler, viewType: Int, position: Int) {
        if (getOnItemChildClickListener() == null) {
            val recyclerKPageItem = getPagingKVHKProvider(viewType) ?: return
            val childClickViewIds = recyclerKPageItem.getChildClickViewIds()
            childClickViewIds.forEach { id ->
                holder.itemView.findViewById<View>(id)?.let { childView ->
                    if (!childView.isClickable)
                        childView.isClickable = true
                    childView.applyDebounceClickListener(1000) { clickView ->
//                        val position: Int = holder.adapterPosition
                        if (position == RecyclerView.NO_POSITION)
                            return@applyDebounceClickListener
                        recyclerKPageItem.onChildClick(holder, clickView, getItem(position), position)
                    }
                }
            }
        }
    }
}