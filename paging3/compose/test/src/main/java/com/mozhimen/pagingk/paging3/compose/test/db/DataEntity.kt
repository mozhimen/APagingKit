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
data class DataEntity constructor(
    @PrimaryKey
    override var id: String = "",
    var author: String = "",
    var title: String = ""
) : IHasId{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DataEntity

        if (id != other.id) return false
        if (author != other.author) return false
        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + title.hashCode()
        return result
    }
}