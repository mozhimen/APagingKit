package com.mozhimen.pagingk.bases

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.mozhimen.basick.utilk.android.view.getViewOfInflate
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.pagingk.widgets.PagingKPagedListMultiAdapter
import com.mozhimen.xmlk.vhk.VHKRecycler
import java.lang.ref.WeakReference

/**
 * @ClassName RecyckerKItemPage
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 11:52
 * @Version 1.0
 */
abstract class BasePagingKVHKProvider<DATA : Any> : IUtilK {

    lateinit var context: Context
    private var _adapterKPageRecyclerMultiRef: WeakReference<PagingKPagedListMultiAdapter<DATA>>? = null
    private val _childClickViewIds by lazy(LazyThreadSafetyMode.NONE) { ArrayList<Int>() }
    private val _childLongClickViewIds by lazy(LazyThreadSafetyMode.NONE) { ArrayList<Int>() }

    ///////////////////////////////////////////////////////////////////////

    fun getPagingKPagedListMultiAdapter(): PagingKPagedListMultiAdapter<DATA>? {
        return _adapterKPageRecyclerMultiRef?.get()
    }

    fun getChildClickViewIds() =
        _childClickViewIds

    fun getChildLongClickViewIds() =
        _childLongClickViewIds

    ///////////////////////////////////////////////////////////////////////

    internal fun setAdapter(adapter: PagingKPagedListMultiAdapter<DATA>) {
        _adapterKPageRecyclerMultiRef = WeakReference(adapter)
    }

    fun addChildClickViewIds(@IdRes vararg viewId: Int) {
        viewId.forEach {
            _childClickViewIds.add(it)
        }
    }

    fun addChildLongClickViewIds(@IdRes vararg viewId: Int) {
        viewId.forEach {
            _childLongClickViewIds.add(it)
        }
    }

    ///////////////////////////////////////////////////////////////////////

    abstract val itemViewType: Int

    abstract val layoutId: Int
        @LayoutRes
        get

    ///////////////////////////////////////////////////////////////////////

    /**
     * （可选重写）创建 ViewHolder。
     * 默认实现返回[BaseViewHolder]，可重写返回自定义 ViewHolder
     */
    open fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHKRecycler =
        VHKRecycler(parent.getViewOfInflate(layoutId))

    @CallSuper
    open fun onBindViewHolder(holder: VHKRecycler, item: DATA?, position: Int) {
        holder.onBind()
    }

    open fun onBindViewHolder(holder: VHKRecycler, item: DATA?, position: Int, payloads: List<Any>) {}

    /**
     * （可选重写）ViewHolder创建完毕以后的回掉方法。
     */
    open fun onViewHolderCreated(holder: VHKRecycler, viewType: Int) {}

    ///////////////////////////////////////////////////////////////////////

    @CallSuper
    open fun onViewAttachedToWindow(holder: VHKRecycler, item: DATA?, position: Int?) {
        holder.onViewAttachedToWindow()
    }

    @CallSuper
    open fun onViewDetachedFromWindow(holder: VHKRecycler, item: DATA?, position: Int?) {
        holder.onViewDetachedFromWindow()
    }

    @CallSuper
    open fun onViewRecycled(holder: VHKRecycler, item: DATA?, position: Int?) {
        holder.onViewRecycled()
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * item 若想实现条目点击事件则重写该方法
     */
    open fun onClick(holder: VHKRecycler, view: View, data: DATA?, position: Int) {}

    /**
     * item 若想实现条目长按事件则重写该方法
     */
    open fun onLongClick(holder: VHKRecycler, view: View, data: DATA, position: Int): Boolean =
        false

    /**
     *
     */
    open fun onChildClick(holder: VHKRecycler, view: View, data: DATA?, position: Int) {}

    /**
     *
     */
    open fun onChildLongClick(holder: VHKRecycler, view: View, data: DATA, position: Int): Boolean =
        false
}