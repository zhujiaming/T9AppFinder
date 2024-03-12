package top.zhujm.appsearch

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.github.promeg.pinyinhelper.Pinyin
import top.zhujm.appsearch.db.AppDatabase
import top.zhujm.appsearch.model.AppItem

/**
 * @author: zhujiaming
 * @data: 2021/11/21
 * @description:
 **/

class SearchUtils {
    companion object {
        val FLAG_APP_SYS = 0x12
        val FLAG_APP = 0x12

        fun getAllApps(context: Context): ArrayList<AppItem> {
            var appList = arrayListOf<AppItem>()
            val installedPackages =
                context.packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES)
            for (i in installedPackages.indices) {
                val packageInfo = installedPackages[i]
                if (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                    // 系统应用
                    continue
                } else if (packageInfo.applicationInfo.flags == FLAG_APP_SYS) {
                    // 保留
                    continue
                }
                var pkgName = packageInfo.packageName

                var appName =
                    packageInfo.applicationInfo.loadLabel(context.packageManager).toString()

//                var appNamePy = getPingyingFirstLetter(appName)

                var keyNumber = getKeyNumber(appName)

                val appItem = AppItem(pkgName, appName, keyNumber, 0, FLAG_APP)

                appList.add(appItem)
            }

            return appList
        }

        fun getKeyNumber(appName: String): String {
            val sb2 = StringBuilder()
            for (part in appName.trim().toCharArray()) {//trim { it <= ' ' } 可以避免某些特殊空格字符的被切除掉
                val f = Pinyin.toPinyin(part).toUpperCase()[0].toString()
                sb2.append(parseToKey(f).toString() + "")
            }
            return sb2.toString()
        }

        private fun parseToKey(f: String): Int {
            return when {
                "ABC2".contains(f) -> {
                    2
                }
                "DEF3".contains(f) -> {
                    3
                }
                "GHI4".contains(f) -> {
                    4
                }
                "JKL5".contains(f) -> {
                    5
                }
                "MNO6".contains(f) -> {
                    6
                }
                "PQRS7".contains(f) -> {
                    7
                }
                "TUV8".contains(f) -> {
                    8
                }
                "WXYZ9".contains(f) -> {
                    9
                }
                "0" == f -> {
                    0
                }
                "1" == f -> {
                    1
                }
                else -> {
                    -1
                }
            }
        }
    }
}