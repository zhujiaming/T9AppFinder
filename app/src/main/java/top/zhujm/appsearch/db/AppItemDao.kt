package top.zhujm.appsearch.db

import androidx.room.*
import top.zhujm.appsearch.model.AppItem

/**
 * @author: zhujiaming
 * @data: 2021/11/22
 * @description:
 **/
@Dao
interface AppItemDao : BaseDao<AppItem> {

    @Query("select * from ${Const.TABLE_NAME_APP_ITEM}")
    fun getAllAppItems(): MutableList<AppItem>


    @Query("select * from ${Const.TABLE_NAME_APP_ITEM} where key_number like :keyIds||'%'")
    fun getItemsByKeyIdsXXX(keyIds: String): MutableList<AppItem>

    @Query("select * from ${Const.TABLE_NAME_APP_ITEM} where key_number like '%'||:keyIds")
    fun getItemsByXXXKeyId(keyIds: String): MutableList<AppItem>
}