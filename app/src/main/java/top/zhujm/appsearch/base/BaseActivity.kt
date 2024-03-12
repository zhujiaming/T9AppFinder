package top.zhujm.appsearch.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author: zhujiaming
 * @data: 2021/11/21
 * @description:
 **/
abstract class BaseActivity<P : BasePresenter<*>> : AppCompatActivity(), BaseView {
    var mPresenter: P? = null
    protected abstract fun createPresenter(): P
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getActivity(): Activity {
        return this;
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
}