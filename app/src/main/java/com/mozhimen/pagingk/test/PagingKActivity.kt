package com.mozhimen.pagingk.test

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVBVM
import com.mozhimen.pagingk.test.databinding.ActivityPagingkBinding
import com.mozhimen.pagingk.test.paging.DataResPagingDataAdapter
import com.mozhimen.pagingk.test.paging.FooterLoadStateAdapter
import com.mozhimen.pagingk.test.restful.mos.DataRes
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
class PagingKActivity : BaseActivityVBVM<ActivityPagingkBinding, PagingKViewModel>() {
    private val dataRecycleViewAdapter: DataResPagingDataAdapter by lazy {
        DataResPagingDataAdapter { position, it, adapter ->
            it?.author = "更改了${position}"
            adapter.notifyItemChanged(position)
        }.apply {
            //初始状态添加监听
            addLoadStateListener {
                when (it.refresh) {

                    is LoadState.NotLoading -> {
                        Log.d(TAG, "addLoadStateListener: is NotLoading")
                    }

                    is LoadState.Loading -> {
                        Log.d(TAG, "addLoadStateListener: is Loading")
                    }

                    is LoadState.Error -> {
                        Log.d(TAG, "addLoadStateListener: is Error:")
                        when ((it.refresh as LoadState.Error).error) {
                            is IOException -> {
                                Log.d(TAG, "addLoadStateListener: IOException")
                            }

                            else -> {
                                Log.d(TAG, "addLoadStateListener: others exception")
                            }
                        }
                    }
                }
            }
        }
    }

    lateinit var mPagingData: PagingData<DataRes>

    override fun initView(savedInstanceState: Bundle?) {
        vb.rvData.layoutManager = LinearLayoutManager(this)
        vb.rvData.adapter = dataRecycleViewAdapter.withLoadStateFooter(FooterLoadStateAdapter { dataRecycleViewAdapter.retry() })
        vb.btnGet.setOnClickListener {
            Log.d(TAG, "点击了查询按钮")
            lifecycleScope.launch {
                try {
                    vm.onInvalidate().collectLatest {
                        mPagingData = it
                        dataRecycleViewAdapter.submitData(it)
                    }
                } catch (e: Exception) {
                    Log.d("测试错误数据 view层", "---错误了怎么办呢")
                }
            }
        }
    }

    override fun bindViewVM(vb: ActivityPagingkBinding) {

    }
}