package com.mozhimen.pagingk.paging3.data.bases

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * @ClassName BasePagedListAdapter
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/2/28 1:31
 * @Version 1.0
 */
abstract class BasePagingKDataAdapter<T : Any, VH : RecyclerView.ViewHolder>(itemCallback: DiffUtil.ItemCallback<T>) : PagingDataAdapter<T, VH>(itemCallback) {
    fun getData(position: Int): T? =
        getItem(position)

    fun getDatas():List<T> =
        snapshot().items
}