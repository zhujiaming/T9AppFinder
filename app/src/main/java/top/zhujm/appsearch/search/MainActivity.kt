package top.zhujm.appsearch.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import top.zhujm.appsearch.AppAdapter
import top.zhujm.appsearch.R
import top.zhujm.appsearch.base.BaseActivity
import top.zhujm.appsearch.model.AppItem

class MainActivity : BaseActivity<SearchPresenter>(), SearchView {

    private var mAdapter: AppAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        setContentView(R.layout.activity_main)
        initView()
        mPresenter?.initData()
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.onKeyClick(R.id.key_0)
    }

    private fun initView() {

        // list 初始化
        mAdapter = AppAdapter(this, arrayListOf())
        list_app.layoutManager = GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, true);
        list_app.adapter = mAdapter
        list_app.itemAnimator = DefaultItemAnimator()

        // 键盘上面区域点击
        container.setOnClickListener { onKeyClick(it) }
        list_app.setOnClickListener { onKeyClick(it) }
    }

    fun onKeyClick(view: View) {
        when (view.id) {
            R.id.container, R.id.list_app -> {
                if (mAdapter?.itemCount == 0) {
                    finish()
                }
            }
            else -> {
                mPresenter?.onKeyClick(view.id)

            }
        }
    }

    override fun createPresenter(): SearchPresenter {
        return SearchPresenter(this)
    }

    override fun notifyDataSetChanged(datas: List<AppItem>) {
        mAdapter?.setDatas(datas)
    }

    override fun setSearchText(text: String) {
        tv_input_result.setText(text)
    }

}

fun Context.toast(
    context: Context = applicationContext,
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(context, message, duration).show()
}