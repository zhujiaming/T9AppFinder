package top.zhujm.appsearch.search

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import top.zhujm.appsearch.App
import top.zhujm.appsearch.SearchUtils
import top.zhujm.appsearch.db.AppDatabase
import top.zhujm.appsearch.db.AppItemDao
import top.zhujm.appsearch.model.AppItem
import kotlin.text.StringBuilder

/**
 * @author: zhujiaming
 * @data: 2021/11/25
 * @description:
 **/

class SearchCenter {

    companion object {

        var TAG = "SearchCenter"

        var DELAY_TIME: Long = 10
        @Volatile
        private var INSTANCE: SearchCenter? = null

        fun get(): SearchCenter =
            INSTANCE ?: synchronized(this) { INSTANCE ?: SearchCenter().also { INSTANCE = it } }
    }

    private var mDispost: Disposable? = null

    private var mKeyNumbers: StringBuilder = StringBuilder()

    private var mHandler: Handler = Handler(Looper.getMainLooper())

    private var mOnSearchResult: OnSearchResultCallback? = null;

    fun setOnSearchResultCallback(onSearchResultCallback: OnSearchResultCallback) {
        this.mOnSearchResult = onSearchResultCallback
    }

    fun enterKey(inputKeyNumber: String) {
        if (!checkInterceptKeyNumber(inputKeyNumber)) {
            return
        }
        mKeyNumbers.append(inputKeyNumber)
        runSearch()
    }

    /**
     * 拦截特殊数字输入
     */
    private fun checkInterceptKeyNumber(inputKeyNumber: String): Boolean {
        when (inputKeyNumber) {
            "0" -> {
                //清除
                clearInput()
                return false
            }
        }
        return true
    }

    private fun clearInput() {
        mKeyNumbers.clear()
        runSearch()
    }

    private fun runSearch() {
        mHandler.postDelayed({
            mDispost = doQuery()
                .subscribe {
                    Log.i(TAG, "runSearch subscribe result len:" + it.size)
                    mOnSearchResult?.onSearchResult(it)
                }
        }, DELAY_TIME)
        mOnSearchResult?.onInputChanged(mKeyNumbers.toString())
    }

    private fun doQuery(): Observable<MutableList<AppItem>> {
        return Observable.just(mKeyNumbers.toString())
            .map {
                Log.i(TAG, "do query ->【$it】")
                val appItemDao = AppDatabase.getInstance(App.mAppContext).appItemDao()
                when {
                    it.startsWith("*") -> {
                        var _it = formatInput(it)
                        if (_it.isEmpty()) ArrayList<AppItem>() else
                            appItemDao.getItemsByXXXKeyId(_it)
                    }
                    else -> {
                        var _it = formatInput(it)
                        if (it.isEmpty()) ArrayList<AppItem>() else
                            appItemDao.getItemsByKeyIdsXXX(_it)
                    }
                }
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun formatInput(input: String): String {
        return input.replace("*", "").replace("#", "")
    }


    fun reloadAllApps(): Observable<AppItem> {
        return Observable.create(ObservableOnSubscribe<AppItem> {
            var context = App.mAppContext
            var appList = arrayListOf<AppItem>()
            val installedPackages =
                context.packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES)
            for (i in installedPackages.indices) {
                val packageInfo = installedPackages[i]
                if (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                    // 系统应用
                    continue
                } else if (packageInfo.applicationInfo.flags == SearchUtils.FLAG_APP_SYS) {
                    // 保留
                    continue
                }
                var pkgName = packageInfo.packageName

                var appName =
                    packageInfo.applicationInfo.loadLabel(context.packageManager).toString()

//                var appNamePy = getPingyingFirstLetter(appName)

                var keyNumber = SearchUtils.getKeyNumber(appName)

                val appItem = AppItem(pkgName, appName, keyNumber, 0, SearchUtils.FLAG_APP)

                appList.add(appItem)
                it.onNext(appItem)
            }
            Log.i(TAG, "search result count :" + appList.size)
            it.onComplete()
        }).doOnNext(Consumer {
            AppDatabase.getInstance(App.mAppContext).appItemDao().insertOrUpdate(it)
        }).doOnError(Consumer { Log.i(TAG, "search error", it) })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }


    interface OnSearchResultCallback {
        fun onSearchResult(result: List<AppItem>)

        fun onInputChanged(input: String)
    }

}