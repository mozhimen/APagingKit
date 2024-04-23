package com.mozhimen.pagingk.test.paging

import android.util.Log
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.pagingk.test.R
import com.mozhimen.pagingk.test.databinding.ItemPagingLoadStateBinding
import com.mozhimen.xmlk.vhk.VHKRecyclerVB

/**
 * @author huanglinqing
 * @project PagingDataDemo
 * @date 2020/11/14
 * @desc 尾部adapter
 */
class FooterLoadStateViewHolder(parent: ViewGroup, var onRetry: I_Listener) :
    VHKRecyclerVB<ItemPagingLoadStateBinding>(LayoutInflater.from(parent.context).inflate(R.layout.item_paging_load_state, parent, false)), IUtilK {

    fun loadState(loadState: LoadState) {
        when (loadState) {
            is LoadState.Error -> {
                vdb.btnRetry.visibility = View.VISIBLE
                vdb.btnRetry.setOnClickListener {
                    onRetry()
                }
                UtilKLogWrapper.d(TAG, "loadState: error了吧")
            }
            is LoadState.Loading -> vdb.llLoading.visibility = View.VISIBLE
            else -> UtilKLogWrapper.d(TAG, "loadState: --其他的错误")
        }
    }
}