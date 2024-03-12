package top.zhujm.appsearch

import android.app.Application
import android.content.Context

/**
 * @author: zhujiaming
 * @data: 2021/11/21
 * @description:
 **/
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setAppContext(applicationContext)
    }

    companion object {
        lateinit var mAppContext: Context;
        fun setAppContext(context: Context) {
            mAppContext = context
        }
    }
}