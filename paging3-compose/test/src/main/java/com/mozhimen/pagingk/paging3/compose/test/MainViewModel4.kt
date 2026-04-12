package com.mozhimen.pagingk.paging3.compose.test

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import androidx.room.RoomDatabase
import com.mozhimen.pagingk.basic.commons.IPagingKDataSource
import com.mozhimen.pagingk.basic.mos.PagingKBaseRes
import com.mozhimen.pagingk.basic.mos.PagingKConfig
import com.mozhimen.pagingk.paging3.compose.bases.BasePagingKRemoteMediator
import com.mozhimen.pagingk.paging3.compose.bases.BasePagingKViewModel
import com.mozhimen.pagingk.paging3.compose.test.db.DataDb
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
class MainViewModel4 : BasePagingKViewModel<DataRes, DataEntity>() {
    override val dataSource: IPagingKDataSource<DataRes, DataEntity> =
        object :IPagingKDataSource<DataRes,DataEntity>{
            override suspend fun onTransformData(currentPageIndex: Int?, datas: List<DataRes>): List<DataEntity> {
                return datas.map { DataEntity(it.id.toString(),it.author?:"",it.title?:"") }
            }
        }

    override suspend fun onLoading(currentPageIndex: Int, pageSize: Int): PagingKBaseRes<DataRes> {
        return RepositoryRemote.getDataOnBack2(currentPageIndex)
    }

    override fun getPagingSourceFactory(): () -> PagingSource<Int, DataEntity> {
        return { DataDb.getDataDao().gets_ofPagingSource() }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRemoteMediator(): RemoteMediator<Int, DataEntity> {
        return object : BasePagingKRemoteMediator<DataRes, DataEntity>() {
            override suspend fun onTransformData(currentPageIndex: Int?, datas: List<DataRes>): List<DataEntity> {
                return dataSource.onTransformData(currentPageIndex,datas)
            }

            override val pagingKConfig: PagingKConfig = this@MainViewModel4.pagingKConfig

            override fun getDb(): RoomDatabase {
                return DataDb.getDb()
            }

            override suspend fun onLoadStart(currentPageIndex: Int) {
                this@MainViewModel4.onLoadStart(currentPageIndex)
            }

            override suspend fun onLoading(currentPageIndex: Int, pageSize: Int): PagingKBaseRes<DataRes> {
               return this@MainViewModel4.onLoading(currentPageIndex, pageSize)
            }

            override suspend fun onLoadFinished(currentPageIndex: Int, isResEmpty: Boolean) {
                this@MainViewModel4.onLoadFinished(currentPageIndex, isResEmpty)
            }

            override fun gets_ofPagingSource(): PagingSource<Int, DataEntity> {
               return DataDb.getDataDao().gets_ofPagingSource()
            }

            override fun deleteAll_ofDb() {
                DataDb.getDataDao().deleteAll()
            }

            override fun insert_ofDb(pageIndex: Int, entity: DataEntity) {
                DataDb.getDataDao().insert(entity)
            }

            override fun insertAll_ofDb(pageIndex: Int, entities: List<DataEntity>) {
                DataDb.getDataDao().insert(entities)
            }
        }
    }

    fun updateData(dataEntity: DataEntity){
        DataDb.getDataDao().update(dataEntity)
    }
}



