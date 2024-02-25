package com.mozhimen.pagingk.test.crud

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @ClassName SampleRepository
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/2/26 0:07
 * @Version 1.0
 */
class SampleRepository {
    suspend fun getNextPage(lastSeenId: Int): List<Int> = withContext(Dispatchers.IO) {
        (lastSeenId..lastSeenId + PAGE_SIZE).map { it }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}