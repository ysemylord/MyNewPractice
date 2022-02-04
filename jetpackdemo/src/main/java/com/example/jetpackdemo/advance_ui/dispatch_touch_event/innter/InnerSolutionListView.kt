package com.example.jetpackdemo.advance_ui.dispatch_touch_event.innter

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ListView
import kotlin.math.absoluteValue

class InnerSolutionListView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet
) : ListView(context, attributeSet) {
    var lastX = 0f
    var lastY = 0f
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                //down的是否让parent不拦截
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = ev.x - lastX
                val dy = ev.y - lastY
                //水平移动的时候，让parent拦截
                if (dx.absoluteValue > dy.absoluteValue) {//水平移动
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
        }

        lastX = ev.x
        lastY = ev.y

        return super.onTouchEvent(ev)
    }
}