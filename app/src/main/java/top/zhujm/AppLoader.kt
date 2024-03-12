package top.zhujm

import top.zhujm.appsearch.App
import top.zhujm.appsearch.SearchTool
import top.zhujm.appsearch.utils.Utils

/**
 * @author: zhujiaming
 * @data: 2021/11/21
 * @description:
 **/

class AppLoader : Thread() {
    override fun run() {
        val allApps = Utils.getAllApps(App.mAppContext)
    }
}