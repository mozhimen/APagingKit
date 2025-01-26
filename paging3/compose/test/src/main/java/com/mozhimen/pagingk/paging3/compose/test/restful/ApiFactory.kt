package com.mozhimen.pagingk.paging3.compose.test.restful

import com.mozhimen.netk.retrofit2.NetKRetrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @ClassName ApiFactory
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/18
 * @Version 1.0
 */
object ApiFactory {
    /**
     * 主Url地址
     */
    private const val BASE_URL = "https://www.wanandroid.com/";

    private val _netKRetrofit2 by lazy {
        NetKRetrofit(
            baseUrl = BASE_URL,
            _converterFactory = GsonConverterFactory.create()
        )
    }

    ////////////////////////////////////////////////////

    val netKRetrofit2 get() = _netKRetrofit2
}