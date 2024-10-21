package com.mozhimen.pagingk.paging3.compose.test.ui.widgets

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.java.util.UtilKDateWrapper
import com.mozhimen.pagingk.paging3.compose.test.MainViewModel4
import com.mozhimen.pagingk.paging3.compose.test.db.DataEntity

/**
 * @ClassName ListScreen2
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/10/20 20:26
 * @Version 1.0
 */
@Composable
fun ListScreen4() {
    val mainViewmodel: MainViewModel4 = viewModel()
    val pagingItems: LazyPagingItems<DataEntity> = mainViewmodel.flowPagingData.collectAsLazyPagingItems()
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

                            //MainViewModel2
                            mainViewmodel.updateData(entity.apply {
                                author += "点击:${UtilKDateWrapper.getNowStr()}"
                            })
                        }
                    )
                }
            }
        }
    }
}