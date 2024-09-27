package com.mozhimen.pagingk.test.paging

import android.util.Log
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mozhimen.kotlin.utilk.commons.IUtilK
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
class DataResPagingSource(private val _repository: Repository) : PagingSource<Int, DataRes>(), IUtilK {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataRes> {
        return try {
            val currentPage = params.key ?: 1//页码未定义置为1
            UtilKLogWrapper.d(TAG, "load: 请求第${currentPage}页")//仓库层请求数据

            val res = _repository.getDatasOnBack(currentPage)

            // 设置前一页和下一页的信息
            val prevKey = if (currentPage > 1) currentPage - 1 else null
            val nextPage = if (currentPage < (res?.data?.pageCount ?: 0))
                currentPage + 1
            else null//没有更多数据//当前页码 小于 总页码 页面加1

            LoadResult.Page(
                data = res?.data?.datas ?: emptyList(),
                prevKey = prevKey,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            if (e is IOException) {
                UtilKLogWrapper.d(TAG, "load: 连接失败")
            }
            UtilKLogWrapper.d(TAG, "load: ${e.message}")

            LoadResult.Error(throwable = e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataRes>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}