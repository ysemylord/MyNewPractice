package com.example.customview.onclick_event_dispathcer

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

class MyView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {


        Log.i(
            "event track View",
            "dispatchTouchEvent start ${MotionEvent.actionToString(ev.action)} "
        )

        val dispatchTouchEvent = super.dispatchTouchEvent(ev)

        Log.i(
            "event track View",
            "dispatchTouchEvent end ${MotionEvent.actionToString(ev.action)} "
        )

        return dispatchTouchEvent
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i(
            "event track View ",
            "onTouchEvent start ${MotionEvent.actionToString(event.action)} "
        )
        val onTouchEvent = super.onTouchEvent(event)

        Log.i(
            "event track View ",
            "onTouchEvent end ${MotionEvent.actionToString(event.action)} $onTouchEvent"
        )
        return onTouchEvent
    }
}