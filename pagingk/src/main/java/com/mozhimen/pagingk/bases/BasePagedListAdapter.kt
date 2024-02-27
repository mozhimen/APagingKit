package com.mozhimen.pagingk.bases

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * @ClassName BasePagedListAdapter
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/2/28 1:31
 * @Version 1.0
 */
abstract class BasePagedListAdapter<T : Any, VH : RecyclerView.ViewHolder>(itemCallback: DiffUtil.ItemCallback<T>) : PagedListAdapter<T, VH>(itemCallback) {
    public override fun getItem(position: Int): T? {
        return super.getItem(position)
    }
}