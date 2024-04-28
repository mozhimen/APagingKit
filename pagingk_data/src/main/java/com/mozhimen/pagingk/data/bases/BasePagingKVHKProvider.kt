package com.mozhimen.pagingk.data.bases

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.pagingk.data.widgets.PagingKDataMultiAdapter
import com.mozhimen.xmlk.vhk.VHKLifecycle
import java.lang.ref.WeakReference

/**
 * @ClassName BasePagingKVHKProvider
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 11:52
 * @Version 1.0
 */
abstract class BasePagingKVHKProvider<DATA : Any, VH : VHKLifecycle> : IUtilK {
    internal var _adapterRef: WeakReference<PagingKDataMultiAdapter<DATA, VH>>? = null

    val adapter: PagingKDataMultiAdapter<DATA, VH>?
        get() = _adapterRef?.get()

    val context: Context?
        get() = _adapterRef?.get()?.context

    ///////////////////////////////////////////////////////////////////////

    abstract val itemViewType: Int

    ///////////////////////////////////////////////////////////////////////

    /**
     * （可选重写）创建 ViewHolder。
     * 默认实现返回[BaseViewHolder]，可重写返回自定义 ViewHolder
     */
    abstract fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH

    @CallSuper
    open fun onBindViewHolder(holder: VH, item: DATA?, position: Int) {
        holder.onBind()
    }

    open fun onBindViewHolder(holder: VH, item: DATA?, position: Int, payloads: List<Any>) {}

    ///////////////////////////////////////////////////////////////////////

    @CallSuper
    open fun onViewAttachedToWindow(holder: VH, item: DATA?, position: Int?) {
        holder.onViewAttachedToWindow()
    }

    @CallSuper
    open fun onViewDetachedFromWindow(holder: VH, item: DATA?, position: Int?) {
        holder.onViewDetachedFromWindow()
    }

    @CallSuper
    open fun onViewRecycled(holder: VH, item: DATA?, position: Int?) {
        holder.onViewRecycled()
    }
}