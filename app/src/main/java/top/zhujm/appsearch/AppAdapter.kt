package top.zhujm.appsearch

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import top.zhujm.appsearch.model.AppItem
import top.zhujm.appsearch.utils.Utils

/**
 * @author: zhujiaming
 * @data: 2021/11/16
 * @description:
 **/


class AppAdapter(private var mContext: Context, list: List<AppItem>) :
    RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    private var datas: List<AppItem> = list

    fun setDatas(datas: List<AppItem>) {
        this.datas = datas
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return datas.get(position).pkgName.hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return AppViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.app_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val appInfo: AppItem = this.datas.get(position)
        holder.ivIcon.setImageDrawable(
            Utils.getIconByPackageName(
                holder.itemView.context,
                appInfo.pkgName
            )
        )
        holder.tvName.text = appInfo.appName

        holder.itemView.setOnClickListener {

            Utils.openApp(
                it.context,
                appInfo.pkgName
            )

//            Utils.goHome(it.context as Activity);
            (it.context as Activity).finish()
        }
    }

    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
    }
}

