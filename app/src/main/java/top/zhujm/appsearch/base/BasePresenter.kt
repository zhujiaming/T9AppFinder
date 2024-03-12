package top.zhujm.appsearch.base

/**
 * @author: zhujiaming
 * @data: 2021/11/21
 * @description:
 **/
public open class BasePresenter<V : BaseView>(var viewDelegate: V?) {

    fun detachView() {
        viewDelegate = null
    }

    open fun getView(): V? {
        if (viewDelegate == null) {
            return null
        }
        return viewDelegate as V
    }
}