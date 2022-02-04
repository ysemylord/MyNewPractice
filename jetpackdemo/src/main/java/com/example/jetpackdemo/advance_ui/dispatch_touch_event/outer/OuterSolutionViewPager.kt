package com.example.jetpackdemo.advance_ui.dispatch_touch_event.innter

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ListView
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class OuterSolutionViewPager @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet
) :
    ViewPager(context, attributeSet) {

    private var mLastX = 0
    private var mLastY: kotlin.Int = 0

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {


        val x = event.x.toInt()
        val y = event.y.toInt()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = event.x.toInt()
                mLastY = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX: Int = x - mLastX
                val deltaY: Int = y - mLastY
                if (abs(deltaX) > abs(deltaY)) {
                    return true
                }
            }
            MotionEvent.ACTION_UP -> {}
            else -> {}
        }

        return super.onInterceptTouchEvent(event)

    }
}