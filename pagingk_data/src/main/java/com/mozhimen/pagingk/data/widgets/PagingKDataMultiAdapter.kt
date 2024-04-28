package com.mozhimen.pagingk.data.widgets

import android.annotation.SuppressLint
import android.content.Context
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.pagingk.data.bases.BasePagingKVHKProvider
import com.mozhimen.xmlk.vhk.VHKLifecycle
import java.lang.ref.WeakReference

/**
 * @ClassName PagingKPagedListMultiAdapter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 11:44
 * @Version 1.0
 */
open class PagingKDataMultiAdapter<DATA : Any, VH : VHKLifecycle>(itemCallback: ItemCallback<DATA>) : PagingKDataAdapter<DATA, VH>(itemCallback), IUtilK {
    private val _pagingKVHKProviders by lazy(LazyThreadSafetyMode.NONE) { SparseArray<BasePagingKVHKProvider<DATA, VH>>() }

    /////////////////////////////////////////////////////////////////////////////////

    /**Fe
     * 通过 ViewType 获取 BaseItemProvider
     * 例如：如果ViewType经过特殊处理，可以重写此方法，获取正确的Provider
     * （比如 ViewType 通过位运算进行的组合的）
     */
    fun getPagingKVHKProvider(viewType: Int): BasePagingKVHKProvider<DATA, VH>? =
        _pagingKVHKProviders.get(viewType)

    /**
     * 必须通过此方法，添加 provider
     */
    fun addPagingKVHKProvider(item: BasePagingKVHKProvider<DATA, VH>) = apply {
        item._adapterRef = WeakReference(this@PagingKDataMultiAdapter)
        _pagingKVHKProviders.put(item.itemViewType, item)
    }

    /////////////////////////////////////////////////////////////////////////////////

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        val recyclerKPageItem = getPagingKVHKProvider(viewType)
        checkNotNull(recyclerKPageItem) { "ViewType: $viewType no such provider found，please use addItemProvider() first!" }
        return recyclerKPageItem.onCreateViewHolder(parent.context, parent, viewType)
    }

    @SuppressLint("MissingSuperCall")
    override fun onBindViewHolder(holder: VH, item: DATA?, position: Int) {
        UtilKLogWrapper.d(TAG, "onBindViewHolderInner: holder $holder item $holder position $holder")
        getPagingKVHKProvider(holder.itemViewType)?.onBindViewHolder(holder, item, position)
    }

    override fun onBindViewHolder(holder: VH, item: DATA?, position: Int, payloads: List<Any>) {
        UtilKLogWrapper.d(TAG, "onBindViewHolderInner: holder $holder item $holder position $holder payloads $payloads")
        getPagingKVHKProvider(holder.itemViewType)?.onBindViewHolder(holder, item, position, payloads)
    }

    /////////////////////////////////////////////////////////////////////////////////

    override fun onViewAttachedToWindow(holder: VH) {
        val position = getPosition(holder)
        getPagingKVHKProvider(holder.itemViewType)?.onViewAttachedToWindow(holder, position?.let { getItem(it) }, position)
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        val position = getPosition(holder)
        getPagingKVHKProvider(holder.itemViewType)?.onViewDetachedFromWindow(holder, position?.let { getItem(it) }, position)
    }

    override fun onViewRecycled(holder: VH) {
        val position = getPosition(holder)
        getPagingKVHKProvider(holder.itemViewType)?.onViewRecycled(holder, position?.let { getItem(it) }, position)
    }
}