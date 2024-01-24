package com.mozhimen.pagingk.test.restful.mos

/**
 * @ClassName BasePageRes
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/24 23:25
 * @Version 1.0
 */
data class BasePageRes<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)