package com.mozhimen.pagingk.paging3.compose.test.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mozhimen.kotlin.utilk.BuildConfig
import com.mozhimen.kotlin.utilk.android.app.UtilKApplicationWrapper

/**
 * @ClassName DataDb
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/21 18:27
 * @Version 1.0
 */
@Database(exportSchema = false, entities = [DataEntity::class], version = 1)
abstract class DataDb : RoomDatabase() {
    abstract fun dataDao(): DataDao

    companion object {
        private val _db: DataDb by lazy {
            Room.databaseBuilder(UtilKApplicationWrapper.instance.get(), DataDb::class.java, "demo_data_db")
                .apply {
                    if (!BuildConfig.DEBUG) {
                        fallbackToDestructiveMigration()
                    }
                }
                .allowMainThreadQueries()
                .build()
        }

        @JvmStatic
        fun getDb(): DataDb =
            _db

        @JvmStatic
        fun getDataDao(): DataDao =
            getDb().dataDao()
    }
}