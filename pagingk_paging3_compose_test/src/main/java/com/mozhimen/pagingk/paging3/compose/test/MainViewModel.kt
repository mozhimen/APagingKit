package com.mozhimen.pagingk.paging3.compose.test

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mozhimen.pagingk.paging3.compose.test.restful.mos.DataRes
import com.mozhimen.pagingk.paging3.compose.test.source.PagingSourceData
import kotlinx.coroutines.flow.Flow

/**
 * @author huanglinqing
 * @project Paging3Demo
 * @date 2020/11/7
 * @desc viewModel 对象
 */
class MainViewModel : ViewModel() {

    /**
     * 获取数据
     */
    fun getFlowPagingData() :Flow<PagingData<DataRes>> =
        Pager(PagingConfig(pageSize = 8)) { PagingSourceData() }.flow
}