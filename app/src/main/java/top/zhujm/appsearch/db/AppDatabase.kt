package top.zhujm.appsearch.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import top.zhujm.appsearch.App
import top.zhujm.appsearch.model.AppItem

/**
 * @author: zhujiaming
 * @data: 2021/11/23
 * @description:
 **/

@Database(entities = arrayOf(AppItem::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appItemDao(): AppItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(App.mAppContext, AppDatabase::class.java, "info.db").build()
    }
}