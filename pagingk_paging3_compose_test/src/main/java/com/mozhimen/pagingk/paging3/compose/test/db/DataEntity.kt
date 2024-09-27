package com.mozhimen.pagingk.paging3.compose.test.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mozhimen.kotlin.elemk.commons.IHasId

/**
 * @ClassName DataEntity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/21 18:28
 * @Version 1.0
 */
@Entity(tableName = "data_entity")
data class DataEntity(
    @PrimaryKey
    override var id: String = "",
    var author: String = "",
    val title: String = ""
) : IHasId