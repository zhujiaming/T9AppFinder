package top.zhujm.appsearch

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

/**
 * @author: zhujiaming
 * @data: 2021/11/18
 * @description:
 **/
@Entity
class AppInfo {
    @PrimaryKey
    var id: Long = 0
    var appName: String? = ""
    var pkgName: String? = ""
    var appIcon: Drawable? = null
    var versionName: String? = ""
    var appDate: String? = ""
    var keys: MutableSet<Int> = mutableSetOf() //linkedHashSet
    var keysStr: String? = ""

    constructor()

    init {
        id = Random(12345).nextLong()
    }

}