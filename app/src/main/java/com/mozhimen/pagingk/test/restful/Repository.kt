package com.mozhimen.pagingk.test.restful

import androidx.annotation.WorkerThread
import com.mozhimen.pagingk.test.restful.mos.BasePageRes
import com.mozhimen.pagingk.test.restful.mos.BaseRes
import com.mozhimen.pagingk.test.restful.mos.DataRes

/**
 * @ClassName Repository
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 16:37
 * @Version 1.0
 */
object Repository {
    /**
     * 查询护理数据
     */
    @WorkerThread
    suspend fun getDatasOnBack(pageId: Int): BaseRes<BasePageRes<DataRes>?>? =
        ApiFactory.apis.getDatas(pageId)
}