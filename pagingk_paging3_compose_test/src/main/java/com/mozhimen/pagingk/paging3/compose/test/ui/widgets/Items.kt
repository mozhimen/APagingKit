package com.mozhimen.pagingk.paging3.compose.test.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mozhimen.composek.utils.ui.borderDebug
import com.mozhimen.kotlin.elemk.commons.IA_Listener
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.pagingk.paging3.compose.test.db.DataEntity

/**
 * @ClassName Views
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/10/19 10:16
 * @Version 1.0
 */
@Composable
fun ItemMessage(
    dataEntity: DataEntity = DataEntity(),
    onItemClick: IA_Listener<DataEntity>? = null
) {
    UtilKLogWrapper.d("ItemMessage>>>>>", "ItemMessage:")
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp)
            .fillMaxSize()
            .borderDebug()
            .clickable {
                onItemClick?.invoke(dataEntity)
            },
        elevation = 10.dp,
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

@Composable
fun ItemMessage2(
    title:String = "",
    content:String = "",
    dataEntity: DataEntity = DataEntity(),
    onItemClick: IA_Listener<DataEntity>? = null
) {
    UtilKLogWrapper.d("ItemMessage>>>>>", "ItemMessage:")
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp)
            .fillMaxSize()
            .borderDebug()
            .clickable {
                onItemClick?.invoke(dataEntity)
            },
        elevation = 10.dp,
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "作者: ${content}"
            )
            Text(
                text = title
            )
        }
    }
}

