package com.mozhimen.pagingk.paging3.compose.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mozhimen.kotlin.elemk.commons.IA_Listener
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.kotlin.utilk.java.util.UtilKDateWrapper
import com.mozhimen.pagingk.paging3.compose.test.db.DataEntity
import com.mozhimen.pagingk.paging3.compose.test.restful.mos.DataRes
import com.mozhimen.pagingk.paging3.compose.test.ui.theme.ComposePagingDemoTheme
import java.io.IOException

class MainActivity : ComponentActivity(), IUtilK {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePagingDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val mainViewmodel: MainViewModel3 = viewModel()
    val pagingItems: LazyPagingItems<DataEntity> = mainViewmodel.flowPagingData.collectAsLazyPagingItems()
    when (val refresh = pagingItems.loadState.refresh) {
        LoadState.Loading -> {
            Log.d("MainActivity>>>>>", "正在加载")
        }

        is LoadState.Error -> {
            when (refresh.error) {
                is IOException -> {
                    Log.d("MainActivity>>>>>", "网络未连接，可在这里放置失败视图")
                }

                else -> {
                    Log.d("MainActivity>>>>>", "网络未连接，其他异常")
                }
            }
        }

        is LoadState.NotLoading -> {}
    }
    Column {
        LazyColumn {
            items(
                count = pagingItems.itemCount
            ) { index ->
                pagingItems[index]?.let {
                    ItemMessage(
                        dataEntity = it,
                        onItemClick = { entity->
                            Log.d("Greeting>>>>>", "Greeting: onItemClick")
                            //MainViewModel2
//                            mainViewmodel.onViewEvent(MainViewModel.SViewEvents.Edit1(it)
//                            mainViewmodel.onViewEvent(MainViewModel.SViewEvents.Edit2(it) { dataRes: DataEntity ->
//                                dataRes.author += "点击:${UtilKDateWrapper.getNowStr()}"
//                                dataRes
//                            })

                            //MainViewModel3
                            mainViewmodel.updateData(entity.apply {
                                author+="点击:${UtilKDateWrapper.getNowStr()}"
                            })
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemMessage(
    dataEntity: DataEntity = DataEntity(),
    onItemClick: IA_Listener<DataEntity>? = null
) {
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp)
            .fillMaxSize()
            .clickable {
                onItemClick?.invoke(dataEntity)
            },
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "作者: ${dataEntity.author}"
            )
            Text(
                text = dataEntity.title
            )
        }
    }
}




