package com.mozhimen.pagingk.test.restful

import com.mozhimen.netk.retrofit2.NetKRetrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @ClassName ApiFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:16
 * @Version 1.0
 */
object ApiFactory {
    private val _baseUrl = "https://www.wanandroid.com/"

    val apis: Apis by lazy { NetKRetrofit(_baseUrl, _converterFactory = GsonConverterFactory.create()).create(Apis::class.java) }
}