package com.example.jetpackdemo.advance_ui.recyclerview_top

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView

class LogTextView(context: Context,attributeSet: AttributeSet) : androidx.appcompat.widget.AppCompatTextView(context,attributeSet) {

    override fun onDraw(canvas: Canvas?) {
        Log.i("LogTextView","onDraw")
        super.onDraw(canvas)
    }
}