package com.mozhimen.pagingk.paging3.data.helpers

import com.mozhimen.kotlin.utilk.kotlin.UtilKThrowable
import com.mozhimen.pagingk.paging3.data.mos.PagingKBaseRes

/**
 * @ClassName PagingKUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 17:36
 * @Version 1.0
 */
object PagingKUtil {
    @JvmStatic
    fun <T> createErrorPagingKRep(exception: Exception): PagingKBaseRes<T> {
        val basePagingKRep = PagingKBaseRes<T>()
        basePagingKRep.code = 10010
        basePagingKRep.msg = UtilKThrowable.getStrMessage(exception)
        return basePagingKRep
    }
}