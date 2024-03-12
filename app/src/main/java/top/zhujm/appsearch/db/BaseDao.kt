package top.zhujm.appsearch.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

/**
 * @author: zhujiaming
 * @data: 2021/11/22
 * @description:
 **/

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateAll(datas: MutableList<T>)

    @Delete
    fun delete(data: T)
}