package com.mozhimen.pagingk.paging3.compose.test.repos

import androidx.annotation.WorkerThread
import com.mozhimen.pagingk.basic.mos.PagingKBaseRes
import com.mozhimen.pagingk.paging3.compose.test.restful.ApiFactory
import com.mozhimen.pagingk.paging3.compose.test.restful.Apis
import com.mozhimen.pagingk.paging3.compose.test.restful.mos.BaseRes
import com.mozhimen.pagingk.paging3.compose.test.restful.mos.DataRes
import com.mozhimen.pagingk.paging3.compose.test.restful.mos.PageRes


/**
 * @author huanglinqing
 * @project Paging3Demo
 * @date 2020/11/7
 * @desc 数据仓库层
 */
object RepositoryRemote {

    /**
     * 查询数据
     */
    @WorkerThread
    @JvmStatic
    suspend fun getDataOnBack(pageId: Int): BaseRes<PageRes<DataRes>> {
        return ApiFactory.netKRetrofit2.create<Apis>().getData(pageId)
    }

    @WorkerThread
    @JvmStatic
    suspend fun getDataOnBack2(pageIndex: Int):PagingKBaseRes<>
}