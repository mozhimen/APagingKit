package com.mozhimen.pagingk.basic.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mozhimen.kotlin.utilk.BuildConfig
import com.mozhimen.kotlin.utilk.android.app.UtilKApplicationWrapper

@Database(
    entities = [KeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class KeyDb : RoomDatabase() {
    abstract fun keyDao(): KeyDao

    companion object {
        private val _keyDb: KeyDb by lazy {
            Room.databaseBuilder(UtilKApplicationWrapper.instance.get(), KeyDb::class.java, "pagingk_key_db")
                .apply {
                    if (!BuildConfig.DEBUG) {
                        fallbackToDestructiveMigration()//使用该方法会在数据库升级异常时重建数据库，但是所有数据会丢失
                    }
                }
                .allowMainThreadQueries()
                .build()
        }

        @JvmStatic
        fun get(): KeyDb =
            _keyDb

        @JvmStatic
        fun getKeyDao(): KeyDao =
            _keyDb.keyDao()
    }
}