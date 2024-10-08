package com.mozhimen.pagingk.paging3.list.commons

import com.mozhimen.pagingk.basic.mos.PagingKBaseRes

/**
 * @ClassName IPagingKKeyedDataSource
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/12 14:50
 * @Version 1.0
 */
interface IPagingKKeyedDataSource<RES, DES> : IPagingKDataSource<RES, DES> {
    /**
     * 初次调用
     */
    suspend fun onLoadInitial(): PagingKBaseRes<RES>

    /**
     * 加载下一页
     * @param adjacentPageKey 下一页的页码
     */
    suspend fun onLoadAfter(adjacentPageKey: Int): PagingKBaseRes<RES>
}