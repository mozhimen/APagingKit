package com.mozhimen.pagingk.test.restful.mos

/**
 * @ClassName BaseRes
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/24 23:24
 * @Version 1.0
 */
data class BaseRes<T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T
)