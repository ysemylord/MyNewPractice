package com.example.jetpackdemo.advance_ui

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackdemo.R

/**
 * 解决惯性滑动的思路
 * 1.获取速度，速度转化为应该滑动的距离 willDistance
 * 2.计算滑动结束时实际滑动的距离actDistance,
 * 3.child应该滑动的距离 childWillDistance = actDistance-willDistance,
 * 4. childWillDistance 得到child的速度，child惯性滑动
 *
 * */
@RequiresApi(Build.VERSION_CODES.M)
class MyNestedScrollView(context: Context, attributeSet: AttributeSet?) :
    NestedScrollView(context, attributeSet) {
    private val mFlingHelper by lazy {
        FlingHelper(context)
    }
    lateinit var topView: View
    private var dy: Int = 0
    var mWillDistance: Double = 0.0

    var mStartFling = false

    constructor(context: Context) : this(context, null)

    init {
        setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (mStartFling) {
                //NestedScrollView滑动的距离为topView.height，表示滑到顶了
                //此时调用RecyclerView滑动
                if (scrollY >= topView.height) {
                    mStartFling = false
                    val willChildScroll = mWillDistance - scrollY
                    val childVelocity = mFlingHelper.getVelocityByDistance(willChildScroll)
                    childFling(childVelocity)
                }
            }
        }
    }

    private fun childFling(childVelocity: Int) {
        val recyclerView = getChildRecyclerView(this)
        recyclerView?.fling(0, childVelocity)
    }

    /**
     * 递归找出RecyclerView
     */
    private fun getChildRecyclerView(viewGroup: ViewGroup): RecyclerView? {
        for (i in 0 until viewGroup.childCount) {
            val view = viewGroup.getChildAt(i)
            if (view is MyRecyclerView) {
                return viewGroup.getChildAt(i) as RecyclerView
            } else if (viewGroup.getChildAt(i) is ViewGroup) {
                val childRecyclerView: ViewGroup? =
                    getChildRecyclerView(viewGroup.getChildAt(i) as ViewGroup)
                if (childRecyclerView is MyRecyclerView) {
                    return childRecyclerView
                }
            }
            continue
        }
        return null
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        topView = findViewById(R.id.top_view)
    }

    override fun onNestedPreScroll(
        target: View, dx: Int, dy: Int, consumed: IntArray, type: Int
    ) {
        if (dy > 0 && scrollY < topView.height) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
        Log.i("MyNestedScrollView", "onNestedPreScroll")
    }

    /**
     * 惯性滑动会调用此函数，此时记录下速度
     */
    override fun fling(velocityY: Int) {
        super.fling(velocityY)
        mStartFling = true
        mWillDistance = mFlingHelper.getSplineFlingDistance(velocityY)
    }

    override fun onStartNestedScroll(
        child: View,
        target: View,
        @ViewCompat.ScrollAxis axes: Int
    ): Boolean {
        Log.i("MyNestedScrollView", "onStartNestedScroll1")
        return super.onStartNestedScroll(child, target, axes)
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        Log.i("MyNestedScrollView", "onStartNestedScroll2")
        return super.onStartNestedScroll(child, target, axes, type)
    }

    override fun onNestedScrollAccepted(
        child: View,
        target: View,
        @ViewCompat.ScrollAxis axes: Int
    ) {
        Log.i("MyNestedScrollView", "onNestedScrollAccepted")
        super.onNestedScrollAccepted(child, target, axes)
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        Log.i("MyNestedScrollView", "onNestedScrollAccepted2")
        super.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onStopNestedScroll(target: View) {
        Log.i("MyNestedScrollView", "onStopNestedScroll")
        super.onStopNestedScroll(target)
    }

    override fun onNestedScroll(
        target: View, dxConsumed: Int, dyConsumed: Int,
        dxUnconsumed: Int, dyUnconsumed: Int
    ) {
        Log.i("MyNestedScrollView", "onNestedScroll")
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        Log.i("MyNestedScrollView", "onNestedPreScroll")
        super.onNestedPreScroll(target, dx, dy, consumed)
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        Log.i("MyNestedScrollView", "onNestedFling")
        return super.onNestedFling(target, velocityX, velocityY, consumed)
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        Log.i("MyNestedScrollView", "onNestedPreFling")
        return super.onNestedPreFling(target, velocityX, velocityY)
    }

    @ViewCompat.ScrollAxis
    override fun getNestedScrollAxes(): Int {
        Log.i("MyNestedScrollView", "getNestedScrollAxes")
        return super.getNestedScrollAxes()
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        Log.i("MyNestedScrollView", "onStopNestedScroll")
        super.onStopNestedScroll(target, type)
    }


}