package com.mozhimen.pagingk.paging3.compose.test.restful.mos

/**
 * @ClassName PageRes
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/18
 * @Version 1.0
 */
data class PageRes<T>(
    var curPage: Int = 0,
    var offset: Int = 0,
    var isOver: Boolean = false,
    var pageCount: Int = 0,
    var size: Int = 0,
    var total: Int = 0,
    var datas: List<T>? = null
)