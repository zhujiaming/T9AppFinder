package top.zhujm.appsearch.search

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import top.zhujm.appsearch.App
import top.zhujm.appsearch.SearchTool

/**
 * @author: zhujiaming
 * @data: 2021/11/21
 * @description:
 **/
class SearchTask() : HandlerThread("SearchTask") {
    lateinit var mSearchHandler: SearchHandler

    override fun start() {
        super.start()
        initHandler()
    }

    private fun initHandler() {
        mSearchHandler = SearchHandler(looper)
    }

    class SearchHandler(looper: Looper) : Handler(looper) {
        var mSearchTool: SearchTool = SearchTool(this)

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_PREPARE_APP -> {
                    mSearchTool.prepareApps(App.mAppContext)
                }
                MSG_SEARCH_APP -> {
//                    mSearchTool.enterToSearch(msg.obj)
                }
                MSG_ROLLBACK_SEARCH -> {

                }
            }
        }
    }

    companion object {
        const val MSG_PREPARE_APP = 1
        const val MSG_SEARCH_APP = 2
        const val MSG_ROLLBACK_SEARCH = 3
        const val MSG_RESULT = 4
    }
}

