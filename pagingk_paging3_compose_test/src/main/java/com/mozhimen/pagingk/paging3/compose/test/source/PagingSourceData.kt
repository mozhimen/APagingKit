package com.mozhimen.pagingk.paging3.compose.test.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.pagingk.paging3.compose.test.repos.RepositoryRemote
import com.mozhimen.pagingk.paging3.compose.test.restful.mos.DataRes
import java.io.IOException
import java.lang.Exception

/**
 * @author huanglinqing
 * @project Paging3Demo
 * @date 2020/11/7
 * @desc 数据源
 */
class PagingSourceData : PagingSource<Int, DataRes>(), IUtilK {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataRes> {
        return try {
            val currentPage = params.key ?: 1
            //仓库层请求数据
            Log.d("请求页码标记", "请求第${currentPage}页")
            val dataRes = RepositoryRemote.getDataOnBack(currentPage)
            //上一页
            val prevPage = if (currentPage == 1) null else currentPage - 1
            //下一页: 当前页码 小于 总页码 页面加1
            val nextPage = if (currentPage < dataRes.data?.pageCount ?: 0) {
                currentPage + 1
            } else {
                //没有更多数据
                null
            }

            LoadResult.Page(
                data = dataRes.data!!.datas!!,
                prevKey = prevPage,
                nextKey = nextPage
            )

        } catch (e: Exception) {
            if (e is IOException) {
                Log.d("测试错误数据", "-------连接失败")
            }
            Log.d("测试错误数据", "-------${e.message}")
            LoadResult.Error(throwable = e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataRes>): Int? {
        return null
    }
}