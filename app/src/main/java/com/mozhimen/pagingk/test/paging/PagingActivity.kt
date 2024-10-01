package com.mozhimen.pagingk.test.paging

import android.os.Bundle
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.bindk.bases.activity.databinding.BaseActivityVDBVM
import com.mozhimen.pagingk.test.databinding.ActivityPagingBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * @ClassName PagingKActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 16:34
 * @Version 1.0
 */
class PagingActivity : BaseActivityVDBVM<ActivityPagingBinding, PagingViewModel>() {
    private val _dataResPagingDataAdapter: DataResPagingDataAdapter by lazy {
        DataResPagingDataAdapter(
            _onItemUpdate = { position, dataRes, adapter ->
                dataRes?.author = "更改了${position}"
                adapter.notifyItemChanged(position)
            }
        ).apply {
            addLoadStateListener {//初始状态添加监听
                when (it.refresh) {
                    is LoadState.NotLoading -> UtilKLogWrapper.d(TAG, "addLoadStateListener: is NotLoading")
                    is LoadState.Loading -> UtilKLogWrapper.d(TAG, "addLoadStateListener: is Loading")
                    is LoadState.Error -> when ((it.refresh as LoadState.Error).error) {
                        is IOException -> UtilKLogWrapper.d(TAG, "addLoadStateListener: IOException")
                        else -> UtilKLogWrapper.d(TAG, "addLoadStateListener: others exception")
                    }
                }
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        vdb.rvData.layoutManager = LinearLayoutManager(this)
        vdb.rvData.adapter = _dataResPagingDataAdapter.withLoadStateFooter(FooterLoadStateAdapter {
            _dataResPagingDataAdapter.retry()
        })
        loadDataRes()
    }

    private fun loadDataRes() {
        lifecycleScope.launch {
            vm.pager.flow.collectLatest {
                _dataResPagingDataAdapter.submitData(it)
            }
        }
    }

    override fun bindViewVM(vdb: ActivityPagingBinding) {

    }
}