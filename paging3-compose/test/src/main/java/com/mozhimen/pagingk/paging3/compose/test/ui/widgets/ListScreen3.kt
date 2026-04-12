package com.mozhimen.pagingk.paging3.compose.test.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mozhimen.composek.utils.ui.borderDebug
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.java.util.UtilKDateWrapper
import com.mozhimen.pagingk.paging3.compose.impls.collectAsLazyPagingItems2
import com.mozhimen.pagingk.paging3.compose.test.MainViewModel3
import com.mozhimen.pagingk.paging3.compose.test.db.DataEntity

/**
 * @ClassName ListScreen2
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/10/20 20:26
 * @Version 1.0
 */
@Composable
fun ListScreen3() {
    val mainViewmodel: MainViewModel3 = viewModel()
    val pagingItems = mainViewmodel.flowPagingData.collectAsLazyPagingItems2()
    val pagingItemsState: State<List<DataEntity?>> = pagingItems.itemSnapshotStateListFlow.collectAsState()
    Column {
        LazyColumn(
            modifier = Modifier.borderDebug()
        ) {
//            items(
//                count = pagingItemsFlow.value.size
//            ){ index: Int ->
//                pagingItems.get(index)?.let {
//                    ItemMessage(
//                        dataEntity = it,
//                        onItemClick = { entity ->
//                            //MainViewModel2
//                            mainViewmodel.updateData(lazyPagingItems = pagingItems.itemSnapshotStateList, entity.apply {
//                                author += "点击:${UtilKDateWrapper.getNowStr()}"
//                            })
//                        }
//                    )
//                }
//            }

            itemsIndexed(
                items = pagingItemsState.value,
                key = { index: Int, item: DataEntity? -> index },
                itemContent = { index, item ->
                    if (item!=null){
                        ItemMessage2(
                            title = item.title,
                            content = item.author,
                            dataEntity = (pagingItems.get(index) ?: item).also { UtilKLogWrapper.d("ListScreen3>>>>>", "ListScreen3: item $item") },
                            onItemClick = { entity ->
                                //MainViewModel2
                                mainViewmodel.updateData(lazyPagingItems = pagingItems, DataEntity(id = entity.id, author = entity.author, title = entity.title+"点击:${UtilKDateWrapper.getNowStr()}"))
                            }
                        )
                    }
                }
            )
        }
    }
}