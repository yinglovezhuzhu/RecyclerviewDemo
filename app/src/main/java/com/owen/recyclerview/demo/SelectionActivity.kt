package com.owen.recyclerview.demo

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * <br/>Author：yunying.zhang
 * <br/>Email: yunyingzhang@rastar.com
 * <br/>Date: 2020/9/3
 */
class SelectionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_selection)

        val tvMsg = findViewById<TextView>(R.id.tvMsg)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview_selection).apply {
            layoutManager = LinearLayoutManager(this@SelectionActivity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }

            addItemDecoration(MyDividerItemDecoration(this@SelectionActivity, MyDividerItemDecoration.VERTICAL).apply {
                setDrawable(resources.getDrawable(R.drawable.list_divider_drawable)!!)
            })
        }

        val rcAdapter = SelectionAdapter().apply {
            val data = ArrayList<String>()
            for (i in 0 until 20) {
                data.add("Item $i")
            }
            addData(data)
        }

        // 设置适配器
        recyclerView.adapter = rcAdapter

        // 定义项目详情查询器
        val itemDetailsLookup = object : ItemDetailsLookup<Long>() {
            override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
                // 根据触摸事件获取点击的View
                val view = recyclerView.findChildViewUnder(e.x, e.y)
                if (view != null) {
                    // 根据View获取ViewHolder对象
                    val itemViewHolder = recyclerView.getChildViewHolder(view)
                    return object : ItemDetails<Long>() {
                        override fun getPosition(): Int = itemViewHolder.adapterPosition

                        override fun getSelectionKey(): Long? = itemViewHolder.itemId
                    }

                }
                return null
            }

        }

        // 定义选择状态存储策略
        val longStorageStrategy = StorageStrategy.createLongStorage();

        // 定义SelectionTracker（关联的RecyclerView 必须先设置Adapter）
        var selectionTracker = SelectionTracker.Builder<Long>("selection_id",
            recyclerView, StableIdKeyProvider(recyclerView),
            itemDetailsLookup, longStorageStrategy)
            .withSelectionPredicate(SelectionPredicates.createSelectAnything()) // 设置选择模式，单选/多选
            .build()

        // 设置SelectionTracker对象
        rcAdapter.tracker = selectionTracker

        selectionTracker.addObserver(object: SelectionTracker.SelectionObserver<Long>() {
            override fun onItemStateChanged(key: Long, selected: Boolean) {
                super.onItemStateChanged(key, selected)
            }

            override fun onSelectionChanged() {
                super.onSelectionChanged()
                tvMsg.text = "Selected Count: ${selectionTracker.selection.size()}"
            }

            override fun onSelectionRefresh() {
                super.onSelectionRefresh()
            }

            override fun onSelectionRestored() {
                super.onSelectionRestored()
//                tvMsg.text = "Selected Count: 0"
            }

        })

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            selectionTracker.clearSelection()
        }
    }
}