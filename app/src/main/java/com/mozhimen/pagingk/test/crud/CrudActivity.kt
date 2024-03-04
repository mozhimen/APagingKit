package com.mozhimen.pagingk.test.crud

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVBVM
import com.mozhimen.pagingk.test.databinding.ActivityCrudBinding

class CrudActivity : BaseActivityVBVM<ActivityCrudBinding, SampleViewModel>() {
    private val sampleAdapter by lazy { SampleAdapter(vm) }

    override fun initView(savedInstanceState: Bundle?) {
        vdb.rvItems.apply {
            layoutManager = LinearLayoutManager(this@CrudActivity)
            adapter = sampleAdapter.withLoadStateFooter(DefaultLoadStateAdapter())
        }

        vdb.swipeRefreshLayout.apply {
            setOnRefreshListener {
                sampleAdapter.refresh()
                isRefreshing = false
            }
        }
        vdb.btInsertHeaderItem.setOnClickListener {
            vm.onViewEvent(SampleViewEvents.InsertItemHeader)
        }

        vdb.btInsertFooterItem.setOnClickListener {
            vm.onViewEvent(SampleViewEvents.InsertItemFooter)
        }
    }

    override fun initObserver() {
        vm.pagingDataViewStates.observe(this, Observer { pagingData ->
            sampleAdapter.submitData(this.lifecycle, pagingData)
        })
    }

    override fun bindViewVM(vb: ActivityCrudBinding) {

    }
}