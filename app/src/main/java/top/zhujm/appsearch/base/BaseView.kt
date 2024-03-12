package top.zhujm.appsearch.base

import android.app.Activity

/**
 * @author: zhujiaming
 * @data: 2021/11/21
 * @description:
 **/

interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun getActivity(): Activity
}