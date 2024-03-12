package top.zhujm.appsearch.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import top.zhujm.appsearch.db.Const

/**
 * @author: zhujiaming
 * @data: 2021/11/22
 * @description:
 **/

@Entity(tableName = Const.TABLE_NAME_APP_ITEM)
data class AppItem(
    @PrimaryKey() val pkgName: String,
    @ColumnInfo(name = "app_name") var appName: String,
//    @ColumnInfo(name = "app_name_py") var appNamePy: String,
    @ColumnInfo(name = "key_number") val keyNumber: String,
    @ColumnInfo(name = "priority") val priority: Int,
    @ColumnInfo(name = "flag") val flag: Int
)