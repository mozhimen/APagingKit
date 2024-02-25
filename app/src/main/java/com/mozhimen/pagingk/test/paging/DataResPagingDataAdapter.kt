package com.mozhimen.pagingk.test.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import com.mozhimen.pagingk.test.R
import com.mozhimen.pagingk.test.databinding.ItemPagingBinding
import com.mozhimen.pagingk.test.restful.mos.DataRes

/**
 * @ClassName DataPagingDataAdapter
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 22:51
 * @Version 1.0
 */
typealias IOnItemUpdateListener = (Int, DataRes?, DataResPagingDataAdapter) -> Unit

class DataResPagingDataAdapter(private val _onItemUpdate: IOnItemUpdateListener) : PagingDataAdapter<DataRes, DataResViewHolder>(DataResDiffUtilItemCallback()) {
    override fun onBindViewHolder(holder: DataResViewHolder, position: Int) {
        val dataRes = getItem(position)

        holder.vb.itemData = dataRes
        holder.vb.btnUpdate.setOnClickListener {
            _onItemUpdate(position, dataRes, this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataResViewHolder {
        val vb: ItemPagingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_paging, parent, false)
        return DataResViewHolder(vb)
    }
}