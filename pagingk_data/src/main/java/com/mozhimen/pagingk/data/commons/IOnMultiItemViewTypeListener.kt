package com.mozhimen.pagingk.data.commons

/**
 * @ClassName IOnMultiItemViewTypeListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/28
 * @Version 1.0
 */
interface IOnMultiItemViewTypeListener<DATA : Any> {
    /**
     * 根据不同数据类型，返回不同的type值
     */
    fun onItemViewType(position: Int, list: List<DATA>): Int
}