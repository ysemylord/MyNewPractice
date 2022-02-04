package com.example.jetpackdemo.advance_ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerView(context: Context, attributeSet: AttributeSet?) :
    RecyclerView(context, attributeSet) {
    constructor(context: Context) : this(context, null)

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        Log.i("MyRecyclerView", "setNestedScrollingEnabled")
        super.setNestedScrollingEnabled(enabled)
    }

    override fun isNestedScrollingEnabled(): Boolean {
        Log.i("MyRecyclerView", "isNestedScrollingEnabled")
        return super.isNestedScrollingEnabled()
    }

    override fun startNestedScroll(@ViewCompat.ScrollAxis axes: Int): Boolean {
        Log.i("MyRecyclerView", "startNestedScroll1")
        return super.startNestedScroll(axes)
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        Log.i("MyRecyclerView", "startNestedScroll2")
        return super.startNestedScroll(axes, type)
    }

    override fun stopNestedScroll() {
        Log.i("MyRecyclerView", "stopNestedScroll")
        return super.stopNestedScroll()
    }
    override fun stopNestedScroll(type: Int) {
        Log.i("MyRecyclerView", "stopNestedScroll2")
        super.stopNestedScroll(type)
    }

    override fun hasNestedScrollingParent(): Boolean {
        Log.i("MyRecyclerView", "hasNestedScrollingParent")
        return super.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int, dyConsumed: Int,
        dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?
    ): Boolean {
        Log.i("MyRecyclerView", "dispatchNestedScroll")
        return super.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        Log.i("MyRecyclerView", "dispatchNestedScroll2")
        return super.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type
        )
    }



    override fun dispatchNestedPreScroll(
        dx: Int, dy: Int, consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        Log.i("MyRecyclerView", "dispatchNestedPreScroll1")
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        Log.i("MyRecyclerView", "dispatchNestedPreScroll2")
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        Log.i("MyRecyclerView", "dispatchNestedFling")
        return super.dispatchNestedFling(velocityX, velocityY, consumed)
    }



    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        Log.i("MyRecyclerView", "dispatchNestedPreFling")
        return super.dispatchNestedPreFling(velocityX, velocityY)
    }




}