package com.mozhimen.pagingk.paging3.compose.test

import com.mozhimen.pagingk.basic.commons.IPagingKDataSource
import com.mozhimen.pagingk.basic.mos.PagingKBaseRes
import com.mozhimen.pagingk.basic.mos.PagingKDataRes
import com.mozhimen.pagingk.paging3.compose.BasePagingKViewModel
import com.mozhimen.pagingk.paging3.compose.test.db.DataEntity
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
class MainViewModel2 : BasePagingKViewModel<DataRes, DataEntity>() {
    override val dataSource: IPagingKDataSource<DataRes, DataEntity> =
        object :IPagingKDataSource<DataRes,DataEntity>{
            override suspend fun onTransformData(currentPageIndex: Int?, datas: List<DataRes>): List<DataEntity> {
                return datas.map { DataEntity(it.id.toString(),it.author?:"",it.title?:"") }
            }
        }

    override suspend fun onLoading(currentPageIndex: Int, pageSize: Int): PagingKBaseRes<DataRes> {
        return RepositoryRemote.getDataOnBack2(currentPageIndex)
    }
}



