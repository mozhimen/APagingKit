package com.mozhimen.pagingk.test.paging

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.mozhimen.pagingk.test.restful.mos.DataRes

/**
 * @ClassName DataDiffItemCallback
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 22:56
 * @Version 1.0
 */
class DataResDiffUtilItemCallback : DiffUtil.ItemCallback<DataRes>() {

    override fun areItemsTheSame(oldItem: DataRes, newItem: DataRes): Boolean =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataRes, newItem: DataRes): Boolean {
        return oldItem == newItem
    }
}