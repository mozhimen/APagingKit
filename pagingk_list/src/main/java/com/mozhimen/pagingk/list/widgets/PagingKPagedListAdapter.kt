package com.mozhimen.pagingk.list.widgets

import android.util.Log
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.utilk.android.view.applyDebounceClickListener
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnDestroy
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnStart
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.pagingk.list.bases.BasePagedListAdapter
import com.mozhimen.pagingk.list.widgets.commons.IOnPageItemChildClickListener
import com.mozhimen.pagingk.list.widgets.commons.IOnPageItemClickListener
import com.mozhimen.pagingk.list.widgets.commons.IOnPageItemLongClickListener
import com.mozhimen.xmlk.vhk.VHKRecycler
import java.util.LinkedHashSet

/**
 * @ClassName AdapterPageRecycler
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 10:28
 * @Version 1.0
 */
open class PagingKPagedListAdapter<DATA : Any>(@LayoutRes private val _layoutId: Int, itemCallback: DiffUtil.ItemCallback<DATA>) : BasePagedListAdapter<DATA, VHKRecycler>(itemCallback), IUtilK,
    LifecycleOwner {
    private var _onPageItemClickListener: IOnPageItemClickListener<DATA>? = null
    private var _onPageItemLongClickListener: IOnPageItemLongClickListener<DATA>? = null
    private var _onPageItemChildClickListener: IOnPageItemChildClickListener<DATA>? = null

    private val _childClickViewIds = LinkedHashSet<Int>()//用于保存需要设置点击事件的 item

    private var _lifecycleRegistry: LifecycleRegistry? = null
    val lifecycleRegistry: LifecycleRegistry
        get() = _lifecycleRegistry ?: LifecycleRegistry(this).also {
            _lifecycleRegistry = it
        }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    //////////////////////////////////////////////////////////////////////////////

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHKRecycler {
        val viewHolder = VHKRecycler(LayoutInflater.from(parent.context).inflate(_layoutId, parent, false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: VHKRecycler, position: Int) {
        onBindViewHolderInner(holder, getItem(position), position)
    }

    @CallSuper
    override fun onBindViewHolder(holder: VHKRecycler, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            onBindViewHolderInner(holder, getItem(position), position, payloads)
        }
    }

    @CallSuper
    protected open fun onBindViewHolderInner(holder: VHKRecycler, item: DATA?, position: Int) {
        UtilKLogWrapper.d(TAG, "onBindViewHolderInner: holder $holder item $holder position $holder")
        holder.onBind()
    }

    protected open fun onBindViewHolderInner(holder: VHKRecycler, item: DATA?, position: Int, payloads: List<Any>) {
        UtilKLogWrapper.d(TAG, "onBindViewHolderInner: holder $holder item $holder position $holder payloads $payloads")
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        lifecycleRegistry.handleLifecycleEventOnStart()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        lifecycleRegistry.handleLifecycleEventOnDestroy()
    }

    override fun onViewAttachedToWindow(holder: VHKRecycler) {
        holder.onViewAttachedToWindow()
    }

    override fun onViewDetachedFromWindow(holder: VHKRecycler) {
        holder.onViewDetachedFromWindow()
    }

    override fun onViewRecycled(holder: VHKRecycler) {
        holder.onViewRecycled()
    }

    //////////////////////////////////////////////////////////////////////////////

    fun getPosition(viewHolder: VHKRecycler): Int? {
        val position = viewHolder.bindingAdapterPosition
        if (position == RecyclerView.NO_POSITION) {
            return null
        }
        return position
    }

    //////////////////////////////////////////////////////////////////////////////

    /**
     * 绑定 item 点击事件
     */
    @CallSuper
    protected open fun bindViewClickListener(holder: VHKRecycler, viewType: Int, position: Int) {
        _onPageItemClickListener?.let {
            holder.itemView.applyDebounceClickListener(lifecycleScope, 500) { view ->
//                val position = holder.adapterPosition
                if (position == RecyclerView.NO_POSITION)
                    return@applyDebounceClickListener
//                val itemViewType = holder.itemViewType
//                if (itemViewType == -0x201) //过滤掉暂无更多
//                    return@applyDebounceClickListener
                it.invoke(this, view, viewType, position)
            }
        }
        _onPageItemLongClickListener?.let {
            holder.itemView.setOnLongClickListener { view ->
//                val position = holder.adapterPosition
                if (position == RecyclerView.NO_POSITION)
                    return@setOnLongClickListener false
                it.invoke(this, view, viewType, position)
                return@setOnLongClickListener true
            }
        }
        _onPageItemChildClickListener?.let {
            for (id in getChildClickViewIds()) {
                holder.itemView.findViewById<View>(id)?.let { view ->
                    if (!view.isClickable)
                        view.isClickable = true
                    view.applyDebounceClickListener(lifecycleScope, 500) { clickView ->
//                        val position = holder.adapterPosition
                        if (position == RecyclerView.NO_POSITION)
                            return@applyDebounceClickListener
                        it.invoke(this, view, viewType, position)
                    }
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////

    fun getChildClickViewIds(): LinkedHashSet<Int> =
        _childClickViewIds

    fun getOnItemClickListener(): IOnPageItemClickListener<DATA>? =
        _onPageItemClickListener

    fun getOnItemChildClickListener(): IOnPageItemChildClickListener<DATA>? =
        _onPageItemChildClickListener

    //////////////////////////////////////////////////////////////////////////////

    /**
     * 设置需要点击事件的子view
     */
    fun addChildClickViewIds(@IdRes vararg viewIds: Int) {
        for (viewId in viewIds) {
            if (!viewIds.contains(viewId))
                _childClickViewIds.add(viewId)
        }
    }

    fun setOnItemClickListener(listener: IOnPageItemClickListener<DATA>) {
        _onPageItemClickListener = listener
    }

    fun setOnItemChildClickListener(listener: IOnPageItemChildClickListener<DATA>) {
        _onPageItemChildClickListener = listener
    }

    fun setOnItemLongClickListener(listener: IOnPageItemLongClickListener<DATA>) {
        _onPageItemLongClickListener = listener
    }
}