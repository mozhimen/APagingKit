package com.mozhimen.pagingk.test.paging

import com.mozhimen.pagingk.test.databinding.ItemPagingBinding
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
class DataResViewHolder(viewDataBinding: ItemPagingBinding) : VHKRecyclerVB<ItemPagingBinding>(viewDataBinding)