package com.example.customview.onclick_event_dispathcer

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

class MyFrameLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Log.i(
            "event track ViewGroup ",
            "dispatchTouchEvent start ${MotionEvent.actionToString(ev.action)} "
        )

        val dispatchTouchEvent = super.dispatchTouchEvent(ev)

        Log.i(
            "event track ViewGroup ",
            "dispatchTouchEvent end ${MotionEvent.actionToString(ev.action)} $dispatchTouchEvent"
        )

        return dispatchTouchEvent
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        val onInterceptTouchEvent = super.onInterceptTouchEvent(ev)

        Log.i(
            "event track ViewGroup",
            "onInterceptTouchEvent ${MotionEvent.actionToString(ev.action)} $onInterceptTouchEvent"
        )


        return onInterceptTouchEvent
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val onTouchEvent = super.onTouchEvent(event)


        Log.i(
            "event track ViewGroup",
            "onTouchEvent ${MotionEvent.actionToString(event.action)} $onTouchEvent"
        )
        return onTouchEvent
    }
}