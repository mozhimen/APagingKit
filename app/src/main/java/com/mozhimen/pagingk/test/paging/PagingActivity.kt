package com.mozhimen.pagingk.test.paging

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVBVM
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
class PagingActivity : BaseActivityVBVM<ActivityPagingBinding, PagingViewModel>() {
    private val _dataResPagingDataAdapter: DataResPagingDataAdapter by lazy {
        DataResPagingDataAdapter(
            _onItemUpdate = { position, dataRes, adapter ->
                dataRes?.author = "更改了${position}"
                adapter.notifyItemChanged(position)
            }
        ).apply {
            addLoadStateListener {//初始状态添加监听
                when (it.refresh) {
                    is LoadState.NotLoading -> Log.d(TAG, "addLoadStateListener: is NotLoading")
                    is LoadState.Loading -> Log.d(TAG, "addLoadStateListener: is Loading")
                    is LoadState.Error -> when ((it.refresh as LoadState.Error).error) {
                        is IOException -> Log.d(TAG, "addLoadStateListener: IOException")
                        else -> Log.d(TAG, "addLoadStateListener: others exception")
                    }
                }
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.rvData.layoutManager = LinearLayoutManager(this)
        vb.rvData.adapter = _dataResPagingDataAdapter.withLoadStateFooter(FooterLoadStateAdapter {
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

    override fun bindViewVM(vb: ActivityPagingBinding) {

    }
}