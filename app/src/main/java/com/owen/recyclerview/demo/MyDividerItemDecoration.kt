package com.owen.recyclerview.demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

/**
 *
 * <br/>Author：yunying.zhang
 * <br/>Email: yunyingzhang@rastar.com
 * <br/>Date: 2020/9/3
 */
class MyDividerItemDecoration(context: Context, orientation: Int): RecyclerView.ItemDecoration() {

    companion object {
        const val HORIZONTAL = RecyclerView.HORIZONTAL
        const val VERTICAL = RecyclerView.VERTICAL
    }

    private val TAG = "DividerItem"
    private val ATTRS = intArrayOf(android.R.attr.listDivider)

    private var mDivider: Drawable? = null

    /**
     * Current orientation. Either [.HORIZONTAL] or [.VERTICAL].
     */
    private var mOrientation = 0

    private var mBounds = Rect()

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        if (mDivider == null) {
            Log.w(TAG,
                "@android:attr/listDivider was not set in the theme used for this "
                        + "DividerItemDecoration. Please set that attribute all call setDrawable()"
            )
        }
        a.recycle()
        setOrientation(orientation)
    }

    fun setOrientation(orientation: Int) {
        if(orientation != HORIZONTAL && orientation != VERTICAL) {
            throw IllegalArgumentException("Orientation value is invalid")
        }
        this.mOrientation = orientation
    }

    fun setDrawable(drawable: Drawable) {
        mDivider = drawable
    }

    /**
     * 这是列表项目绘制的偏移量，按分割线的绘制要求设定项目绘制的偏移量，这样就可以预留位置绘制分割线，防止分割线与列表项目重叠覆盖
     * @param outRect
     * @param view
     * @param state
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0)
            return
        }
        if (mOrientation == DividerItemDecoration.VERTICAL) {
            // 绘制项目时，底部向下偏移绘制分割线高度的内容（也就是绘制项目的时候，底部多绘制分割线高度的空白部分，用来绘制分割线）
            outRect.set(20, 0, 0, mDivider!!.intrinsicHeight)
        } else {
            outRect.set(0, 20, mDivider!!.intrinsicWidth, 0)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null || mDivider == null) {
            return
        }
        if (mOrientation == DividerItemDecoration.VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//            super.onDrawOver(c, parent, state)
//            if (parent.layoutManager == null || mDivider == null) {
//                return
//            }
//            if (mOrientation == DividerItemDecoration.VERTICAL) {
//                drawVertical(c, parent)
//            } else {
//                drawHorizontal(c, parent)
//            }
    }

    /**
     * 绘制垂直方向的分割线（分割线是横向的）
     * @param canvas 画布
     * @param parent RecyclerView
     */
    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        // 计算分割线绘制区域
        val left: Int
        val right: Int
        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right,
                parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom = mBounds.bottom + child.translationY.roundToInt()
            val top = bottom - mDivider!!.intrinsicHeight

//                Log.e("AAAAAAA", "Item Rect(${mBounds.left}, ${mBounds.top}, ${mBounds.right}, ${mBounds.bottom})")
//                Log.e("XXXXXXXX", "Divider Rect($left, $top, $right, $bottom)")
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(canvas)

            ColorDrawable().apply {
                setBounds(left, child.top, 20, child.bottom)
                color = if(i % 2 == 0) {
                    Color.RED
                } else {
                    Color.CYAN
                }
                draw(canvas)
            }

        }
        canvas.restore()
    }

    /**
     * 绘制水平方向的分割线（分割线是垂直的）
     * @param canvas 画布
     * @param parent RecyclerView
     */
    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int
        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(parent.paddingLeft, top,
                parent.width - parent.paddingRight, bottom)
        } else {
            top = 0
            bottom = parent.height
        }
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.layoutManager!!.getDecoratedBoundsWithMargins(child, mBounds)
            val right = mBounds.right + child.translationX.roundToInt()
            val left = right - mDivider!!.intrinsicWidth
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(canvas)

            ColorDrawable().apply {
                setBounds(left, child.top, child.right, 20)
                color = if(i % 2 == 0) {
                    Color.RED
                } else {
                    Color.CYAN
                }
                draw(canvas)
            }
        }
        canvas.restore()
    }
}