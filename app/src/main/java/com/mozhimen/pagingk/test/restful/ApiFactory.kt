package com.mozhimen.pagingk.test.restful

import com.mozhimen.netk.retrofit.NetKRetrofit

/**
 * @ClassName ApiFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:16
 * @Version 1.0
 */
object ApiFactory {
    private val _baseUrl = "https://www.wanandroid.com/"

    val apis: Apis by lazy { NetKRetrofit(_baseUrl).create(Apis::class.java) }
}