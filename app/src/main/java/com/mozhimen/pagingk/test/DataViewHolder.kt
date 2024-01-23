package com.mozhimen.pagingk.test

import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.pagingk.test.databinding.ItemPagingkBinding

/**
 * @ClassName DataViewHolder
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 23:07
 * @Version 1.0
 */
class DataViewHolder(private val _vb: ItemPagingkBinding) : RecyclerView.ViewHolder(_vb.root) {
    var vb = _vb
}