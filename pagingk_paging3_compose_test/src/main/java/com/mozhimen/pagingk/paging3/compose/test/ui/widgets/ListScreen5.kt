package com.mozhimen.pagingk.paging3.compose.test.ui.widgets

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mozhimen.pagingk.paging3.compose.test.MainViewModel5

/**
 * @ClassName ListScreen5
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/10/20 20:38
 * @Version 1.0
 */
enum class ListState {
    IDLE,
    LOADING,
    PAGINATING,
    ERROR,
    PAGINATION_EXHAUST,
}

@Composable
fun ListScreen5() {
    val mainViewmodel: MainViewModel5 = viewModel()
    val lazyColumnListState = rememberLazyListState()
//    val coroutineScope = rememberCoroutineScope()

    val shouldStartPaginate = remember {
        derivedStateOf {
            mainViewmodel.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }

    val articles = mainViewmodel.newsList

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && mainViewmodel.listState == ListState.IDLE)
            mainViewmodel.getNews()
    }

    LazyColumn(state = lazyColumnListState) {
        items(
            items = articles,
            key = { it.id },
        ) { article ->
            ItemMessage(
                dataEntity = article,
                onItemClick = { entity ->
                    Log.d("Greeting>>>>>", "Greeting: onItemClick")
//                    mainViewmodel.updateData(entity.apply {
//                        author += "点击:${UtilKDateWrapper.getNowStr()}"
//                    })
                }
            )
        }
    }
}
