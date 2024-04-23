package com.mozhimen.pagingk.test.paging

import android.util.Log
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName LoadStateFooterAdapter
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 23:13
 * @Version 1.0
 */
class FooterLoadStateAdapter(private val _onRetry: I_Listener) : LoadStateAdapter<FooterLoadStateViewHolder>(), IUtilK {

    override fun onBindViewHolder(holder: FooterLoadStateViewHolder, loadState: LoadState) {
        UtilKLogWrapper.d(TAG, "onBindViewHolder:")
        holder.loadState(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterLoadStateViewHolder {
        return FooterLoadStateViewHolder(parent, _onRetry)
    }

}