package com.mozhimen.pagingk.paging3.compose.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import com.mozhimen.pagingk.paging3.compose.test.restful.mos.DataRes
import com.mozhimen.pagingk.paging3.compose.test.ui.theme.ComposePagingDemoTheme
import java.io.IOException

class MainActivity : ComponentActivity() {
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
    val mainViewmodel: MainViewModel2 = viewModel()
    val pagingItems: LazyPagingItems<DataRes> = mainViewmodel.flowPagingData.collectAsLazyPagingItems()
    Column {
        LazyColumn {
            items(
                count = pagingItems.itemCount
            ) { index ->
                Message(data = pagingItems[index])
            }
            if (pagingItems.loadState.refresh is LoadState.Loading) {
                Log.d("MainActivity>>>>>", "正在加载")
            } else if (pagingItems.loadState.refresh is LoadState.Error) {
                when ((pagingItems.loadState.refresh as LoadState.Error).error) {
                    is IOException -> {
                        Log.d("MainActivity>>>>>", "网络未连接，可在这里放置失败视图")
                    }

                    else -> {
                        Log.d("MainActivity>>>>>", "网络未连接，其他异常")
                    }
                }
            }
        }
    }
}

@Composable
fun Message(data: DataRes?) {
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp)
            .fillMaxSize(), elevation = 10.dp
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = "作者: ${data?.author}"
            )
            Text(
                text = "${data?.title}"
            )
        }
    }
}




