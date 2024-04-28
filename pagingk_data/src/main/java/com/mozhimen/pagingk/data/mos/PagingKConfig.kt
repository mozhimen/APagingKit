package com.mozhimen.pagingk.data.mos

/**
 * @ClassName CPaingKConfig
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 16:57
 * @Version 1.0
 */
class PagingKConfig(
    /**
     * 第一页的页码
     */
    val pageIndexFirst: Int = 0,
    /**
     * 每页需要加载的数量
     */
    val pageSize: Int = 10
)