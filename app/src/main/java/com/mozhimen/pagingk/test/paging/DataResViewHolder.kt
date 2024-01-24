package com.mozhimen.pagingk.test.paging

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.pagingk.test.databinding.ItemPagingkBinding
import com.mozhimen.uicorek.vhk.VHKRecyclerVB

/**
 * @ClassName DataViewHolder
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 23:07
 * @Version 1.0
 */
//class DataResViewHolder(private val _vb: ItemPagingkBinding) : VHKRecyclerVB(_vb.root) {
//    var vb = _vb
//}
class DataResViewHolder(viewDataBinding: ItemPagingkBinding) : VHKRecyclerVB<ItemPagingkBinding>(viewDataBinding)