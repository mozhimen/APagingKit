package com.mozhimen.pagingk.basic.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = KeyEntity.TABLE_NAME)
data class KeyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val currPage: Int,
    val nextPage: Int?,
    @ColumnInfo("created_at")
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        const val TABLE_NAME = "key_table"
    }
}