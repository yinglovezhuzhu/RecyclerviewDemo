package com.owen.recyclerview.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * <br/>Author：yunying.zhang
 * <br/>Email: yunyingzhang@rastar.com
 * <br/>Date: 2020/9/3
 */

class SelectionAdapter() : RecyclerView.Adapter<ItemViewHolder>() {

    val data = ArrayList<String>()

    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    fun addData(d: ArrayList<String>) {
        if(d.isNotEmpty()) {
            data.addAll(d)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // 创建ViewHolder对象
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_1, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        // 获取项目的数量
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // 绑定ViewHolder，这里设置需要展示的数据
        holder.title.text = data[position]
        holder.desc.apply {
            visibility = View.VISIBLE
            text = "This is item $position"
        }


        // 根据SelectionTracker记录的选择状态，更新UI显示
        tracker?.let {
            // 如果使用其他标记是否选中（如：RadioButton）,可在这里更改控件的状态
//            holder.check.isChecked = it.isSelected(position.toLong())
            holder.itemView.isActivated = it.isSelected(position.toLong())
        }
    }

}