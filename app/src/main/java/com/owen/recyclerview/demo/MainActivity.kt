package com.owen.recyclerview.demo

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {

            layoutManager = LinearLayoutManager(this@MainActivity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }


            adapter = MainListAdapter().apply {
                val list = ArrayList<MainItemData>().apply {
                    add(MainItemData("RecyclerView Selection", "Use background mark status"))
                }
                addData(list)
            }
        }


//        recyclerView.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL).apply {
//            setDrawable(resources.getDrawable(R.drawable.list_divider_drawable)!!)
//        })
        recyclerView.addItemDecoration(MyDividerItemDecoration(this@MainActivity, MyDividerItemDecoration.VERTICAL).apply {
            setDrawable(resources.getDrawable(R.drawable.list_divider_drawable)!!)
        })
//        recyclerView.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.HORIZONTAL))

        recyclerView.addOnItemTouchListener(object: RecyclerView.OnItemTouchListener {

            var gestureDetector: GestureDetectorCompat

            init {
                // 定义GestureDetectorCompat对象，快速解析触摸事件，分发为onClick和onLongClick
                gestureDetector = GestureDetectorCompat(this@MainActivity, object : GestureDetector.SimpleOnGestureListener() {
                    override fun onSingleTapUp(e: MotionEvent?): Boolean {
                        // 处理点击事件
                        e?.also {
                            val child = recyclerView.findChildViewUnder(e.x, e.y)

                            if(null != child) {
                                val position = recyclerView.getChildAdapterPosition(child)
                                Toast.makeText(this@MainActivity, "Item $position was clicked", Toast.LENGTH_SHORT).show()
                                when(position) {
                                    0 -> {
                                        startActivity(Intent(this@MainActivity, SelectionActivity::class.java).apply {
                                            putExtra("type", 1)
                                        })
                                    }
                                }
                            }
                        }
                        return super.onSingleTapUp(e)
                    }

                    override fun onLongPress(e: MotionEvent?) {
                        // 处理长按事件
                        e?.also {
                            val child = recyclerView.findChildViewUnder(e.x, e.y)

                            if(null != child) {
                                val position = recyclerView.getChildAdapterPosition(child)
                                Toast.makeText(this@MainActivity, "Item $position was long clicked", Toast.LENGTH_SHORT).show()
                            }
                        }
                        super.onLongPress(e)
                    }

                })
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

                // 调用GestureDetectorCompat对象处理分发事件
                gestureDetector.onTouchEvent(e)

                // 此处不要返回true，否则点击效果将会失效
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }

        })



    }



    class MainListAdapter() : RecyclerView.Adapter<ItemViewHolder>() {

        private val data = ArrayList<MainItemData>()

        fun addData(d: ArrayList<MainItemData>) {
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
            holder.title.text = data[position].title
            holder.desc.text = data[position].desc
        }

    }

    interface OnItemClickListener {
        abstract fun onItemClick(holder: ItemViewHolder, position: Int)
    }


    class RCAnim: SimpleItemAnimator() {
        override fun animateChange(
            oldHolder: RecyclerView.ViewHolder?,
            newHolder: RecyclerView.ViewHolder?,
            fromLeft: Int,
            fromTop: Int,
            toLeft: Int,
            toTop: Int
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun runPendingAnimations() {
            TODO("Not yet implemented")
        }

        override fun endAnimation(item: RecyclerView.ViewHolder) {
            TODO("Not yet implemented")
        }

        override fun endAnimations() {
            TODO("Not yet implemented")
        }

        override fun isRunning(): Boolean {
            TODO("Not yet implemented")
        }

        override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
            TODO("Not yet implemented")
        }

        override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
            TODO("Not yet implemented")
        }

        override fun animateMove(
            holder: RecyclerView.ViewHolder?,
            fromX: Int,
            fromY: Int,
            toX: Int,
            toY: Int
        ): Boolean {
            TODO("Not yet implemented")
        }


    }


}