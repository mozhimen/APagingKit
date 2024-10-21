package com.mozhimen.pagingk.paging3.compose.test.ui.widgets

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.pagingk.paging3.compose.test.MainViewModel1
import com.mozhimen.pagingk.paging3.compose.test.db.DataEntity

/**
 * @ClassName ListScreen
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/10/20 20:25
 * @Version 1.0
 */
@Composable
fun ListScreen1() {
    val mainViewmodel: MainViewModel1 = viewModel()
    val pagingItems: LazyPagingItems<DataEntity> = mainViewmodel.flowPagingData.collectAsLazyPagingItems()
//    when (val refresh = pagingItems.loadState.refresh) {
//        LoadState.Loading -> {
//            Log.d("MainActivity>>>>>", "正在加载")
//        }
//
//        is LoadState.Error -> {
//            when (refresh.error) {
//                is IOException -> {
//                    Log.d("MainActivity>>>>>", "网络未连接，可在这里放置失败视图")
//                }
//
//                else -> {
//                    Log.d("MainActivity>>>>>", "网络未连接，其他异常")
//                }
//            }
//        }
//
//        is LoadState.NotLoading -> {}
//    }
    Column {
        LazyColumn {
            items(
                count = pagingItems.itemCount.also { UtilKLogWrapper.d("ListScreen>>>>>", "ListScreen: pagingItems.itemCount ${pagingItems.itemCount}") }
            ) { index ->
                pagingItems[index]?.let {
                    ItemMessage(
                        dataEntity = it,
                        onItemClick = { entity ->
                            Log.d("Greeting>>>>>", "Greeting: onItemClick")
                            //MainViewModel1
                            mainViewmodel.onViewEvent(MainViewModel1.SViewEvents.Edit1(it))
//                            mainViewmodel.onViewEvent(MainViewModel1.SViewEvents.Edit2(it) { dataRes: DataEntity ->
//                                dataRes.author += "点击:${UtilKDateWrapper.getNowStr()}"
//                                dataRes
//                            })

//                            //MainViewModel2
//                            mainViewmodel.updateData(lazyPagingItems = pagingItems, entity.apply {
//                                author += "点击:${UtilKDateWrapper.getNowStr()}"
//                            })
//
//                            //MainViewModel3
////                            mainViewmodel.updateData(entity.apply {
////                                author+="点击:${UtilKDateWrapper.getNowStr()}"
////                            })
                        }
                    )
                }
            }
        }
    }
}