package com.mozhimen.pagingk.test.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.pagingk.test.restful.Repository

/**
 * @ClassName PagingKViewModel
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 23:17
 * @Version 1.0
 */
@OptIn(ExperimentalPagingApi::class)
class PagingViewModel : BaseViewModel() {

    val pager = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 2, initialLoadSize = 10),
        initialKey = 1,
        pagingSourceFactory = { DataResPagingSource(Repository) }
    )
}