package com.example.jetpackdemo.advance_ui.dispatch_touch_event.innter

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ListView
import kotlin.math.absoluteValue

class OuterSolutionListView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet
) : ListView(context, attributeSet) {

    override fun onTouchEvent(ev: MotionEvent): Boolean {

        return super.onTouchEvent(ev)
    }
}