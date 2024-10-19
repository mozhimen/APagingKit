package com.mozhimen.pagingk.basic.commons

import androidx.paging.PagingSource

/**
 * @ClassName IPagingKRemoteMediator
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/20
 * @Version 1.0
 */
interface IPagingKRemoteMediatorDbDao<T : Any> {
    //@Query("select * from mod_info")
    fun gets_ofPagingSource(): PagingSource<Int, T>

    //@Query("delete from mod_info")
    fun deleteAll_ofDb()

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll_ofDb(pageIndex: Int, entities: List<T>)

    fun insert_ofDb(pageIndex: Int, entity: T)
}
