package com.mozhimen.pagingk.basic.commons

import com.mozhimen.pagingk.basic.mos.PagingKBaseRes

/**
 * @ClassName IPagingKKeyedDataSource
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/12 14:50
 * @Version 1.0
 */
interface IPagingKSource<RES, DES> : IPagingKDataSource<RES, DES> {

    suspend fun onLoadStart(currentPageIndex: Int) {}

    /**
     * 初次调用
     */
    /**
     * 加载下一页
     * @param p 下一页的页码
     */
    suspend fun onLoadRes(currentPageIndex: Int, pageSize: Int): PagingKBaseRes<RES>

    suspend fun onLoadFinished(currentPageIndex: Int, isResEmpty: Boolean) {}
}