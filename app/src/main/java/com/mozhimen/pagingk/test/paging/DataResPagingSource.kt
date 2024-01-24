package com.mozhimen.pagingk.test.paging

import android.util.Log
import androidx.paging.PagingSource
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.pagingk.test.restful.Repository
import com.mozhimen.pagingk.test.restful.mos.DataRes
import java.io.IOException
import java.lang.Exception

/**
 * @author huanglinqing
 * @project Paging3Demo
 * @date 2020/11/7
 * @desc 数据源
 */
class DataResPagingSource : PagingSource<Int, DataRes>(), IUtilK {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataRes> {
        return try {
            val currentPage = params.key ?: 1//页码未定义置为1
            Log.d(TAG, "load: 请求第${currentPage}页")//仓库层请求数据

            val res = Repository.getDatasOnBack(currentPage)
            //当前页码 小于 总页码 页面加1
            val nextPage = if (currentPage < (res?.data?.pageCount ?: 0))
                currentPage + 1
            else null//没有更多数据

            LoadResult.Page(
                data = res?.data?.datas ?: emptyList(),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            if (e is IOException) {
                Log.d(TAG, "load: -------连接失败")
            }
            Log.d(TAG, "load: -------${e.message}")

            LoadResult.Error(throwable = e)
        }
    }
}