package com.mozhimen.pagingk.paging3.data.bases

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.kotlin.elemk.commons.IA_BListener
import com.mozhimen.kotlin.utilk.kotlin.collections.indexOf

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

    fun getDatas(): List<T> =
        snapshot().items

    fun filter(predicate: IA_BListener<T, Boolean>): List<T> =
        getDatas().filter(predicate)

    fun find(predicate: IA_BListener<T, Boolean>): T? =
        getDatas().find(predicate)

    fun indexOf(predicate: IA_BListener<T, Boolean>): List<Int> =
        getDatas().indexOf(predicate)

    fun indexOfFirst(predicate: IA_BListener<T, Boolean>): Int =
        getDatas().indexOfFirst(predicate)
}