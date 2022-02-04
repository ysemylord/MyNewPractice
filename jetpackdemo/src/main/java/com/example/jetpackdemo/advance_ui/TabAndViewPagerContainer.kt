package com.example.jetpackdemo.advance_ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout

class TabAndViewPagerContainer(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {
    constructor(context: Context) : this(context, null)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val makeHeightMeasureSpec =
            MeasureSpec.makeMeasureSpec(((parent as ViewGroup).parent as ViewGroup).measuredHeight, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, makeHeightMeasureSpec)
    }
}