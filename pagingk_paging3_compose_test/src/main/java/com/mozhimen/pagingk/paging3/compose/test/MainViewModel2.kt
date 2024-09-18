package com.mozhimen.pagingk.paging3.compose.test

import com.mozhimen.pagingk.basic.mos.PagingKBaseRes
import com.mozhimen.pagingk.basic.mos.PagingKDataRes
import com.mozhimen.pagingk.paging3.compose.BasePagingKViewModel
import com.mozhimen.pagingk.paging3.compose.test.repos.RepositoryRemote
import com.mozhimen.pagingk.paging3.compose.test.restful.mos.BaseRes
import com.mozhimen.pagingk.paging3.compose.test.restful.mos.DataRes
import com.mozhimen.pagingk.paging3.compose.test.restful.mos.PageRes

/**
 * @ClassName MainViewModel2
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/18
 * @Version 1.0
 */
class MainViewModel2 : BasePagingKViewModel<DataRes, DataRes>() {
    override suspend fun onTransformData(currentPageIndex: Int?, datas: List<DataRes>): List<DataRes> {
        return datas
    }

    override suspend fun onLoadRes(currentPageIndex: Int, pageSize: Int): PagingKBaseRes<DataRes> {
        return RepositoryRemote.getDataOnBack(currentPageIndex).toPagingDataRes()
    }

    private fun BaseRes<PageRes<DataRes>>.toPagingDataRes(): PagingKBaseRes<DataRes> {
        return PagingKBaseRes<DataRes>(
            1, null, PagingKDataRes(

            )
        )
    }
}

//fun <T> BasePageRes<T>?.pageRes2pagingKRep(): PagingKBaseRes<T> =
//    PagingKUtil.pageRes2pagingKRep(this)
//
//object PagingKUtil {
//    @JvmStatic
//    fun <T> pageRes2pagingKRep(res: BasePageRes<T>?): PagingKBaseRes<T> {
//        return res?.let {
//            PagingKBaseRes(
//                1,
//                null,
//                PagingKDataRes(res.current, res.pages, res.size, res.total, res.records)
//            )
//        } ?: PagingKBaseRes(0, null)
//    }
//}

