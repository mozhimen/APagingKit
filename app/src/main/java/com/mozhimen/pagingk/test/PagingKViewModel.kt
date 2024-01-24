package com.mozhimen.pagingk.test

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.pagingk.test.paging.DataResPagingSource
import com.mozhimen.pagingk.test.restful.mos.DataRes
import kotlinx.coroutines.flow.Flow

/**
 * @ClassName PagingKViewModel
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 23:17
 * @Version 1.0
 */
class PagingKViewModel : BaseViewModel() {

    fun onInvalidate(): Flow<PagingData<DataRes>> =
        Pager(PagingConfig(pageSize = 1)) { DataResPagingSource() }.flow
}