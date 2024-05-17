package com.mozhimen.pagingk.paging3.basic.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

/**
 * @ClassName Paging3Util
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/17
 * @Version 1.0
 */
object Paging3Util {
    @JvmStatic
    fun <T : Any> getPagerFlow(pageSize: Int, source: () -> PagingSource<Int, T>): Flow<PagingData<T>> =
        Pager(PagingConfig(pageSize), pagingSourceFactory = source).flow
}