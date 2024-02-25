package com.mozhimen.pagingk.test.crud

import androidx.paging.PagingSource

/**
 * @ClassName SamplePagingSource
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/2/26 0:08
 * @Version 1.0
 */
class SamplePagingSource(
    private val sampleRepository: SampleRepository
) : PagingSource<Int, SampleEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SampleEntity> {
        return try {
            val data =
                sampleRepository.getNextPage(lastSeenId = params.key ?: 1).map { SampleEntity(it, "Page number: $it") }

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = if (data.isNotEmpty()) data.last().id + 1 else null// return null when page is empty to denote the end of the pagination
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}