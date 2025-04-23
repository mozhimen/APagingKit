package com.mozhimen.pagingk.paging3.data.bases

import android.content.Context
import androidx.annotation.CallSuper
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.pagingk.paging3.data.widgets.PagingKDataMultiAdapter
import com.mozhimen.xmlk.vhk.VHKLifecycle
import com.mozhimen.xmlk.vhk.commons.IVHKProvider
import java.lang.ref.WeakReference

/**
 * @ClassName BasePagingKVHKProvider
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 11:52
 * @Version 1.0
 */
abstract class BasePagingKVHKProvider<DATA : Any, VH : VHKLifecycle> : IVHKProvider<DATA, VH>, IUtilK {
    internal var _adapterRef: WeakReference<PagingKDataMultiAdapter<DATA, VH>>? = null

    val adapter: PagingKDataMultiAdapter<DATA, VH>?
        get() = _adapterRef?.get()

    val context: Context?
        get() = _adapterRef?.get()?.context

    ///////////////////////////////////////////////////////////////////////

    abstract val itemViewType: Int

    ///////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun onBindViewHolder(holder: VH, item: DATA?, position: Int) {
        holder.onBind()
    }

    override fun onBindViewHolder(holder: VH, item: DATA?, position: Int, payloads: List<Any>) {}

    ///////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun onViewAttachedToWindow(holder: VH, item: DATA?, position: Int?) {
        holder.onViewAttachedToWindow()
    }

    @CallSuper
    override fun onViewDetachedFromWindow(holder: VH, item: DATA?, position: Int?) {
        holder.onViewDetachedFromWindow()
    }

    @CallSuper
    override fun onViewRecycled(holder: VH, item: DATA?, position: Int?) {
        holder.onViewRecycled()
    }
}