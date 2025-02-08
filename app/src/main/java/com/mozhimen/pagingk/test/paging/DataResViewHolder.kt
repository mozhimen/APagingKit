package com.mozhimen.pagingk.test.paging

import com.mozhimen.pagingk.test.databinding.ItemPagingBinding
import com.mozhimen.xmlk.vhk.VHKLifecycle2VDB

/**
 * @ClassName DataViewHolder
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 23:07
 * @Version 1.0
 */
//class DataResViewHolder(private val _vb: ItemPagingkBinding) : VHKLifecycle2VDB(_vb.root) {
//    var vb = _vb
//}
class DataResViewHolder(viewDataBinding: ItemPagingBinding) : VHKLifecycle2VDB<ItemPagingBinding>(viewDataBinding)