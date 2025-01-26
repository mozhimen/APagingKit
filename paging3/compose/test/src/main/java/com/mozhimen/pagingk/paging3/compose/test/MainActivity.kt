package com.mozhimen.pagingk.paging3.compose.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.pagingk.paging3.compose.test.ui.theme.ComposePagingDemoTheme
import com.mozhimen.pagingk.paging3.compose.test.ui.widgets.ListScreen3

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
                    ListScreen3()
                }
            }
        }
    }
}






