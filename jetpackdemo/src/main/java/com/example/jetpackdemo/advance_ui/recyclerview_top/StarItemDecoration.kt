package com.example.jetpackdemo.advance_ui.recyclerview_top

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView

class StarItemDecoration : RecyclerView.ItemDecoration() {

    companion object {
        const val HEAD_HEIGHT = 120
        const val DIVIDER_HEIGHT = 8
    }

    private val mHeaderPaint = Paint().apply {
        color = Color.BLACK
        //alpha = 125
    }

    private val mHeaderPaint2 = Paint().apply {
        color = Color.RED
        //alpha = 125
    }

    private val mHeaderTextPaint = Paint().apply {
        color = Color.BLUE
        textSize = 90f
    }

    private val mDividerPaint = Paint().apply {
        Color.BLACK
        strokeWidth = DIVIDER_HEIGHT.toFloat()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        Log.i("StarItemDecoration", "getItemOffsets")
        if (parent.adapter is StarRecyclerViewAdapter) {
            val adapterPosition = parent.getChildAdapterPosition(view)
            if ((parent.adapter as StarRecyclerViewAdapter).isGroupHeader(adapterPosition)) {
                outRect.top = HEAD_HEIGHT
            } else {
                outRect.top = DIVIDER_HEIGHT
            }

        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        Log.i("StarItemDecoration", "onDraw")
        if (parent.adapter !is StarRecyclerViewAdapter) return
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            if (childView.top - HEAD_HEIGHT < parent.paddingTop) {
                continue
            }
            val childAdapterPosition = parent.getChildAdapterPosition(childView)
            val starRecyclerViewAdapter = parent.adapter as StarRecyclerViewAdapter
            val isGroupHeader = starRecyclerViewAdapter.isGroupHeader(childAdapterPosition)
            if (isGroupHeader) {
                Log.i("onDraw", "$i")
                val left = 0f
                val right = childView.width.toFloat()
                val top = childView.top - HEAD_HEIGHT
                val bottom = childView.top
                c.drawRect(left, top.toFloat(), right, bottom.toFloat(), mHeaderPaint)

                val groupName = starRecyclerViewAdapter.getGroupName(childAdapterPosition)
                val textBounds = Rect()
                mHeaderTextPaint.getTextBounds(groupName, 0, groupName.length, textBounds)
                //这里让文字居中
                //drawText的参数x,y应该是基准线的坐标，不过这里为了简单，传入的值是text左下角的坐标
                c.drawText(
                    groupName,
                    0f,
                    top + (HEAD_HEIGHT / 2 + textBounds.height() / 2).toFloat(),
                    mHeaderTextPaint
                )

            } else {
                c.drawLine(
                    0f,
                    childView.top.toFloat() - DIVIDER_HEIGHT / 2,
                    childView.width.toFloat(),
                    childView.top.toFloat() - DIVIDER_HEIGHT / 2,
                    mDividerPaint
                )
            }
        }

    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        Log.i("StarItemDecoration", "onDrawOver")
        if (parent.adapter !is StarRecyclerViewAdapter) return

        val left = 0f
        val right = parent.width.toFloat()
        val top = parent.paddingTop.toFloat()
        val firstVisibleAdapterPosition =
            (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val starRecyclerViewAdapter = parent.adapter as StarRecyclerViewAdapter
        val data = starRecyclerViewAdapter.getItem(firstVisibleAdapterPosition)
        Log.i("onDrawOver", data.toString())
        val itemView =
            parent.findViewHolderForAdapterPosition(firstVisibleAdapterPosition)?.itemView
        val groupName = data.groupName
        val textBounds = Rect()
        mHeaderTextPaint.getTextBounds(groupName, 0, groupName.length, textBounds)
        if (starRecyclerViewAdapter.isGroupHeader(firstVisibleAdapterPosition + 1)) {
            val bottom =  (top+HEAD_HEIGHT).coerceAtMost((itemView?.bottom ?: 0).toFloat())
            c.drawRect(left, top, right,  bottom.toFloat(), mHeaderPaint2)
            c.save()
            c.clipRect(left, top, right, bottom.toFloat())
            c.drawText(
                groupName,
                0f,
                top+ ((bottom-top) / 2 + textBounds.height() / 2).toFloat(),
                mHeaderTextPaint
            )
            c.restore()
        } else {
            c.drawRect(left, top, right, top + HEAD_HEIGHT, mHeaderPaint2)
            c.drawText(
                groupName,
                0f,
                top + (HEAD_HEIGHT / 2 + textBounds.height() / 2).toFloat(),
                mHeaderTextPaint
            )
        }
    }


}