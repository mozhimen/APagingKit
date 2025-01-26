package com.mozhimen.pagingk.paging3.compose.test.restful

import com.mozhimen.pagingk.paging3.compose.test.restful.mos.BaseRes
import com.mozhimen.pagingk.paging3.compose.test.restful.mos.DataRes
import com.mozhimen.pagingk.paging3.compose.test.restful.mos.PageRes
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author：HuangLinqing
 * @date ：2022/5/10
 * @desc：
 */
interface Apis {

    /**
     * 获取数据
     */
    @GET("wenda/list/{pageId}/json")
    suspend fun getData(@Path("pageId") pageId:Int): BaseRes<PageRes<DataRes>>
}