package com.example.jetpackdemo.advance_ui.dispatch_touch_event.innter

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ListView
import androidx.viewpager.widget.ViewPager

class InnerSolutionViewPager @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet
) :
    ViewPager(context, attributeSet) {

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            super.onInterceptTouchEvent(ev)
            return false
        }
        //这里假设SolutionViewPager拦截所有事件
        super.onInterceptTouchEvent(ev)
        return true
    }
}