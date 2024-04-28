package com.mozhimen.pagingk.data.widgets.commons

import android.view.View
import com.mozhimen.pagingk.data.widgets.PagingKDataAdapter

/**
 * @ClassName
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11
 * @Version 1.0
 */
fun interface IOnPageItemClickListener<T : Any> {
    fun onClick(adapter: PagingKDataAdapter<T, *>, view: View, position: Int)
}

fun interface IOnPageItemLongClickListener<T : Any> {
    fun onLongClick(adapter: PagingKDataAdapter<T, *>, view: View, position: Int): Boolean
}

fun interface IOnPageItemChildClickListener<T : Any> {
    fun onChildClick(adapter: PagingKDataAdapter<T, *>, view: View, position: Int)
}

fun interface IOnPageItemChildLongClickListener<T : Any> {
    fun onChildLongClick(adapter: PagingKDataAdapter<T, *>, view: View, position: Int): Boolean
}