package com.mozhimen.pagingk.paging3.compose.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.insertFooterItem
import androidx.paging.insertHeaderItem
import androidx.paging.map
import com.mozhimen.kotlin.elemk.commons.IA_AListener
import com.mozhimen.pagingk.paging3.compose.test.db.DataEntity
import com.mozhimen.pagingk.paging3.compose.test.source.PagingSourceData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

/**
 * @author huanglinqing
 * @project Paging3Demo
 * @date 2020/11/7
 * @desc viewModel 对象
 */
class MainViewModel1 : ViewModel() {
    private val modificationEvents = MutableStateFlow<List<SViewEvents>>(emptyList())

    sealed class SViewEvents {
        data class Edit1(val dataEntity: DataEntity) : SViewEvents()
        data class Edit2(val dataEntity: DataEntity, val block: IA_AListener<DataEntity>) : SViewEvents()
        data class Remove(val dataEntity: DataEntity) : SViewEvents()
        data class InsertItemHeader(val dataEntity: DataEntity) : SViewEvents()
        data class InsertItemFooter(val dataEntity: DataEntity) : SViewEvents()
    }

    // combine them with the data coming from paging

    /**
     * 获取数据
     */
    val flowPagingData: Flow<PagingData<DataEntity>> by lazy {
        Pager(PagingConfig(pageSize = 8)) { PagingSourceData() }.flow
            .cachedIn(viewModelScope)
            .combine(modificationEvents) { pagingData, modifications ->
                modifications.fold(pagingData) { acc, event ->
                    applyEvents(acc, event)
                }
            }
    }

    fun onViewEvent(sampleViewEvents: SViewEvents) {
        modificationEvents.value += sampleViewEvents
    }

    private fun applyEvents(paging: PagingData<DataEntity>, sampleViewEvents: SViewEvents): PagingData<DataEntity> {
        return when (sampleViewEvents) {
            is SViewEvents.Remove -> {
                paging.filter {
                    sampleViewEvents.dataEntity.id != it.id
                }
            }

            is SViewEvents.Edit1 -> {
                paging.map {
                    if (sampleViewEvents.dataEntity.id == it.id)
                        return@map it.copy(author = "${it.author} (updated)")
                    else
                        return@map it
                }
            }

            is SViewEvents.Edit2 -> {
                paging.map {
                    if (sampleViewEvents.dataEntity.id == it.id)
                        return@map sampleViewEvents.block.invoke(it.copy())
                    else
                        return@map it
                }
            }

            is SViewEvents.InsertItemHeader -> {
                paging.insertHeaderItem(
                    item = sampleViewEvents.dataEntity
                )
            }

            is SViewEvents.InsertItemFooter -> {
                paging.insertFooterItem(
                    item = sampleViewEvents.dataEntity
                )
            }
        }
    }
}