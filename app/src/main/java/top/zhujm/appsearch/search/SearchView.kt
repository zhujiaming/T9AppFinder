package top.zhujm.appsearch.search

import top.zhujm.appsearch.base.BaseView
import top.zhujm.appsearch.model.AppItem

/**
 * @author: zhujiaming
 * @data: 2021/11/28
 * @description:
 **/

interface SearchView : BaseView {
    fun notifyDataSetChanged(datas: List<AppItem>)

    fun setSearchText(text: String)
}