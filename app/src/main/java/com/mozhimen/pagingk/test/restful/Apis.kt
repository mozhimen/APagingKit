package com.mozhimen.pagingk.test.restful

import com.mozhimen.pagingk.test.restful.mos.BasePageRes
import com.mozhimen.pagingk.test.restful.mos.BaseRes
import com.mozhimen.pagingk.test.restful.mos.DataRes
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @ClassName Apis
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 16:36
 * @Version 1.0
 */
interface Apis {
    /**
     * 获取数据
     */
    @GET("wenda/list/{pageId}/json")
    suspend fun getDatas(@Path("pageId") pageId: Int): BaseRes<BasePageRes<DataRes>?>?
}