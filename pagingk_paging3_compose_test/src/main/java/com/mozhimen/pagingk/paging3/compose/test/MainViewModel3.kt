package com.mozhimen.pagingk.paging3.compose.test

import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.pagingk.basic.commons.IPagingKDataSource
import com.mozhimen.pagingk.basic.mos.PagingKBaseRes
import com.mozhimen.pagingk.paging3.compose.bases.BasePagingKViewModel
import com.mozhimen.pagingk.paging3.compose.impls.LazyPagingItems2
import com.mozhimen.pagingk.paging3.compose.test.db.DataEntity
import com.mozhimen.pagingk.paging3.compose.test.repos.RepositoryRemote
import com.mozhimen.pagingk.paging3.compose.test.restful.mos.DataRes

/**
 * @ClassName MainViewModel2
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/18
 * @Version 1.0
 */
class MainViewModel3 : BasePagingKViewModel<DataRes, DataEntity>() {
    override val dataSource: IPagingKDataSource<DataRes, DataEntity> =
        object : IPagingKDataSource<DataRes, DataEntity> {
            override suspend fun onTransformData(currentPageIndex: Int?, datas: List<DataRes>): List<DataEntity> {
                return datas.map { DataEntity(it.id.toString(), it.author ?: "", it.title ?: "") }
            }
        }

    override suspend fun onLoading(currentPageIndex: Int, pageSize: Int): PagingKBaseRes<DataRes> {
        return RepositoryRemote.getDataOnBack2(currentPageIndex)
    }

    fun updateData(lazyPagingItems: LazyPagingItems2<DataEntity>, dataEntity: DataEntity) {
        val index = lazyPagingItems.itemSnapshotStateList
            .indexOf(lazyPagingItems.itemSnapshotStateList.find { it?.id == dataEntity.id })
        UtilKLogWrapper.d(TAG, "updateData: index $index")
        if (index > -1) {
//            lazyPagingItems.itemSnapshotList[index]?.apply {
//                title = dataEntity.title
//                author = dataEntity.author
//            }
            lazyPagingItems.set(index, dataEntity)
        }
    }
}



