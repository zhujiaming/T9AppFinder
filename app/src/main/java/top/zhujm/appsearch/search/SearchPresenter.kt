package top.zhujm.appsearch.search

import android.os.HandlerThread
import android.os.Message
import android.util.Log
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import top.zhujm.appsearch.R
import top.zhujm.appsearch.base.BasePresenter
import top.zhujm.appsearch.base.BaseView
import top.zhujm.appsearch.model.AppItem
import top.zhujm.appsearch.utils.Utils

/**
 * @author: zhujiaming
 * @data: 2021/11/21
 * @description:
 **/

class SearchPresenter(searchView: SearchView?) : BasePresenter<SearchView>(searchView),
    SearchCenter.OnSearchResultCallback {

    companion object {
        var TAG = "SearchPresenter"
    }

//    var mHandlerThread: HandlerThread = HandlerThread("search_task")

    init {
//        mHandlerThread.start()
//        mSearchHandler = Handler(mHandlerThread.looper)
        SearchCenter.get().setOnSearchResultCallback(this)
    }

    override fun onSearchResult(result: List<AppItem>) {
        getView()?.notifyDataSetChanged(result)
    }

    override fun onInputChanged(input: String) {
        getView()?.setSearchText(input)
    }

    fun initData() {
        var startTime = System.currentTimeMillis()
        val disposable =
            SearchCenter.get().reloadAllApps()
                .subscribe(
                    { },
                    { },
                    {
                        Log.i(
                            TAG,
                            "reloadAllApps end, cost ${System.currentTimeMillis() - startTime}ms"
                        )
                    })
    }

    fun onKeyClick(keyId: Int) {
        var arg1 = "0";
        when (keyId) {
            R.id.key_0 -> {
                arg1 = "0";
            }
            R.id.key_1 -> {
                arg1 = "1";
            }

            R.id.key_2 -> {
                arg1 = "2";
            }

            R.id.key_3 -> {
                arg1 = "3";
            }

            R.id.key_4 -> {
                arg1 = "4";
            }

            R.id.key_5 -> {
                arg1 = "5";
            }

            R.id.key_6 -> {
                arg1 = "6";
            }

            R.id.key_7 -> {
                arg1 = "7";
            }

            R.id.key_8 -> {
                arg1 = "8";
            }

            R.id.key_9 -> {
                arg1 = "9";
            }

            R.id.key_star -> {
//                Utils.goHome(baseView?.getActivity());
//                mSearchTool.reset();
                arg1 = "*"
            }
            R.id.key_well -> {
                arg1 = "#"
            }
            else -> {

            }
        }
        SearchCenter.get().enterKey(arg1)
    }

}