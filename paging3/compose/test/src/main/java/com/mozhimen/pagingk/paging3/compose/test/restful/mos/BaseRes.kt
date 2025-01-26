package com.mozhimen.pagingk.paging3.compose.test.restful.mos

/**
 * @ClassName BaseReq
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/18
 * @Version 1.0
 */
data class BaseRes<T>(
    var data: T? = null,
    var errorCode: Int = 0,
    var errorMsg: String? = null,
)