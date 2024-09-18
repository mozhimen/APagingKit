package com.mozhimen.pagingk.basic.mos

/**
 * @ClassName CPaingKConfig
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 16:57
 * @Version 1.0
 */
class PagingKConfig constructor(
    /**
     * 第一页的页码
     */
    val pageIndexFirst: Int = 1,
    /**
     * 每页需要加载的数量
     */
    val pageSize: Int = 10,

    val prefetchDistance: Int = pageSize / 2,
    val initialLoadSize :Int = pageSize * 2
)