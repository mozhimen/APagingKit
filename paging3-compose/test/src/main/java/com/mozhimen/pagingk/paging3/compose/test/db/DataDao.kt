package com.mozhimen.pagingk.paging3.compose.test.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * @ClassName DataDao
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/21 18:30
 * @Version 1.0
 */
@Dao
interface DataDao {
    @Update
    fun update(dataEntity: DataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(datas: List<DataEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: DataEntity)

    @Query("delete from data_entity")
    fun deleteAll()

    @Query("select * from data_entity")
    fun gets_ofPagingSource(): PagingSource<Int, DataEntity>
}