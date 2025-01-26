package com.mozhimen.pagingk.paging3.compose.test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.mozhimen.kotlin.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.netk.retrofit2.cons.SNetKRep
import com.mozhimen.pagingk.paging3.compose.test.db.DataEntity
import com.mozhimen.pagingk.paging3.compose.test.repos.RepositoryRemote
import com.mozhimen.pagingk.paging3.compose.test.ui.widgets.ListState
import kotlinx.coroutines.launch

/**
 * @ClassName MainViewModel4
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/10/20 12:25
 * @Version 1.0
 */
class MainViewModel5:BaseViewModel() {
    val newsList = mutableStateListOf<DataEntity>()

    private var page by mutableStateOf(1)
    var canPaginate by mutableStateOf(false)
    var listState by mutableStateOf(ListState.IDLE)

    init {
        getNews()
    }

    fun getNews() = viewModelScope.launch {
        if (page == 1 || (page != 1 && canPaginate) && listState == ListState.IDLE) {
            listState = if (page == 1) ListState.LOADING else ListState.PAGINATING

            RepositoryRemote.getDataFlowOnBack(page).collect { rep->
                if (rep is SNetKRep.MSuccess) {
                    canPaginate = rep.data.data?.datas?.size == 10

                    if (page == 1) {
                        newsList.clear()
                        newsList.addAll(rep.data.data?.datas?.map { DataEntity(it.id.toString(),it.author?:"",it.title?:"") } ?: emptyList())
                    } else {
                        newsList.addAll(rep.data.data?.datas?.map { DataEntity(it.id.toString(),it.author?:"",it.title?:"") } ?: emptyList())
                    }

                    listState = ListState.IDLE

                    if (canPaginate)
                        page++
                } else {
                    listState = if (page == 1) ListState.ERROR else ListState.PAGINATION_EXHAUST
                }
            }
        }
    }

    override fun onCleared() {
        page = 1
        listState = ListState.IDLE
        canPaginate = false
        super.onCleared()
    }
}