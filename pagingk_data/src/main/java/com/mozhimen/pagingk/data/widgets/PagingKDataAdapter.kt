package com.mozhimen.pagingk.data.widgets

import android.content.Context
import android.util.SparseArray
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnDestroy
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnStart
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.pagingk.data.bases.BasePagingKDataAdapter
import com.mozhimen.pagingk.data.widgets.commons.IOnPageItemChildClickListener
import com.mozhimen.pagingk.data.widgets.commons.IOnPageItemChildLongClickListener
import com.mozhimen.pagingk.data.widgets.commons.IOnPageItemClickListener
import com.mozhimen.pagingk.data.widgets.commons.IOnPageItemLongClickListener
import com.mozhimen.xmlk.vhk.VHKLifecycle

/**
 * @ClassName AdapterPageRecycler
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 10:28
 * @Version 1.0
 */
abstract class PagingKDataAdapter<DATA : Any, VH : VHKLifecycle>(itemCallback: DiffUtil.ItemCallback<DATA>) :
    BasePagingKDataAdapter<DATA, VH>(itemCallback), IUtilK, LifecycleOwner {

    private var _recyclerView: RecyclerView? = null

    val recyclerView get() = _recyclerView

    val context get() = _recyclerView?.context

    //////////////////////////////////////////////////////////////////////////////

    private var _lifecycleRegistry: LifecycleRegistry? = null
    val lifecycleRegistry: LifecycleRegistry
        get() = _lifecycleRegistry ?: LifecycleRegistry(this).also {
            _lifecycleRegistry = it
        }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    //////////////////////////////////////////////////////////////////////////////

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateViewHolder(parent.context, parent, viewType).apply {
            bindViewClickListener(this, viewType)
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, getItem(position), position)
    }

    @CallSuper
    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            onBindViewHolder(holder, getItem(position), position, payloads)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        _recyclerView = recyclerView
        lifecycleRegistry.handleLifecycleEventOnStart()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        _recyclerView = null
        lifecycleRegistry.handleLifecycleEventOnDestroy()
    }

    override fun onViewAttachedToWindow(holder: VH) {
        holder.onViewAttachedToWindow()
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        holder.onViewDetachedFromWindow()
    }

    override fun onViewRecycled(holder: VH) {
        holder.onViewRecycled()
    }

    //////////////////////////////////////////////////////////////////////////////

    protected abstract fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH

    @CallSuper
    protected open fun onBindViewHolder(holder: VH, item: DATA?, position: Int) {
        UtilKLogWrapper.d(TAG, "onBindViewHolderInner: holder $holder item $holder position $holder")
        holder.onBind()
    }

    protected open fun onBindViewHolder(holder: VH, item: DATA?, position: Int, payloads: List<Any>) {
        UtilKLogWrapper.d(TAG, "onBindViewHolderInner: holder $holder item $holder position $holder payloads $payloads")
    }

    //////////////////////////////////////////////////////////////////////////////

    fun getPosition(viewHolder: VH): Int? {
        val position = viewHolder.bindingAdapterPosition
        if (position == RecyclerView.NO_POSITION) {
            return null
        }
        return position
    }

    //////////////////////////////////////////////////////////////////////////////

    private var _onPageItemClickListener: IOnPageItemClickListener<DATA>? = null
    private var _onPageItemLongClickListener: IOnPageItemLongClickListener<DATA>? = null
    private var _onPageItemChildClickListeners: SparseArray<IOnPageItemChildClickListener<DATA>>? = null
    private var _onPageItemChildLongClickListeners: SparseArray<IOnPageItemChildLongClickListener<DATA>>? = null

    //////////////////////////////////////////////////////////////////////////////

    /**
     * override this method if you want to override click event logic
     * 如果你想重新实现 item 点击事件逻辑，请重写此方法
     */
    protected open fun onItemClick(v: View, position: Int) {
        _onPageItemClickListener?.onClick(this, v, position)
    }

    /**
     * override this method if you want to override longClick event logic
     * 如果你想重新实现 item 长按事件逻辑，请重写此方法
     */
    protected open fun onItemLongClick(v: View, position: Int): Boolean {
        return _onPageItemLongClickListener?.onLongClick(this, v, position) ?: false
    }

    protected open fun onItemChildClick(v: View, position: Int) {
        _onPageItemChildClickListeners?.get(v.id)?.onChildClick(this, v, position)
    }

    protected open fun onItemChildLongClick(v: View, position: Int): Boolean {
        return _onPageItemChildLongClickListeners?.get(v.id)?.onChildLongClick(this, v, position) ?: false
    }

    //////////////////////////////////////////////////////////////////////////////

    /**
     * 绑定 item 点击事件
     */
    @CallSuper
    protected open fun bindViewClickListener(holder: VH, viewType: Int) {
        _onPageItemClickListener?.let {
            holder.itemView.setOnClickListener { view ->
                getPosition(holder)?.let {
                    onItemClick(view, it)
                }
            }
        }
        _onPageItemLongClickListener?.let {
            holder.itemView.setOnLongClickListener { view ->
                getPosition(holder)?.let {
                    onItemLongClick(view, it)
                } ?: false
            }
        }
        _onPageItemChildClickListeners?.let {
            for (i in 0 until it.size()) {
                val id = it.keyAt(i)
                holder.itemView.findViewById<View>(id)?.let { v1 ->
                    v1.setOnClickListener { v2 ->
                        getPosition(holder)?.let { pos ->
                            onItemChildClick(v2, pos)
                        }
                    }
                }
            }
        }
        _onPageItemChildLongClickListeners?.let {
            for (i in 0 until it.size()) {
                val id = it.keyAt(i)
                holder.itemView.findViewById<View>(id)?.let { v1 ->
                    v1.setOnLongClickListener { v2 ->
                        getPosition(holder)?.let { pos ->
                            onItemChildLongClick(v2, pos)
                        } ?: false
                    }
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////

    fun setOnPageItemClickListener(listener: IOnPageItemClickListener<DATA>?) = apply {
        _onPageItemClickListener = listener
    }

    fun getOnPageItemClickListener(): IOnPageItemClickListener<DATA>? =
        _onPageItemClickListener

    fun setOnPageItemLongClickListener(listener: IOnPageItemLongClickListener<DATA>?) = apply {
        _onPageItemLongClickListener = listener
    }

    fun getOnPageItemLongClickListener(): IOnPageItemLongClickListener<DATA>? =
        _onPageItemLongClickListener

    fun addOnPageItemChildClickListener(@IdRes id: Int, listener: IOnPageItemChildClickListener<DATA>) = apply {
        _onPageItemChildClickListeners =
            (_onPageItemChildClickListeners ?: SparseArray<IOnPageItemChildClickListener<DATA>>(2)).apply {
                put(id, listener)
            }
    }

    fun removeOnPageItemChildClickListener(@IdRes id: Int)= apply {
        _onPageItemChildClickListeners?.remove(id)
    }

    fun addOnPageItemChildLongClickListener(@IdRes id: Int, listener: IOnPageItemChildLongClickListener<DATA>) = apply {
        _onPageItemChildLongClickListeners = (_onPageItemChildLongClickListeners ?: SparseArray<IOnPageItemChildLongClickListener<DATA>>(2)).apply {
            put(id, listener)
        }
    }

    fun removeOnItemChildLongClickListener(@IdRes id: Int) = apply {
        _onPageItemChildLongClickListeners?.remove(id)
    }
}