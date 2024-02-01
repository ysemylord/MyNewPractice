package com.tcssj.wms_test_add_window

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addWindow(view: View) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.addView(
            Button(this),
            WindowManager.LayoutParams().also { attr ->
                attr.width = 500
                attr.height = 500
                attr.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                attr.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL//设置为非模态，事件才能向下面的窗口传递
                attr.gravity = Gravity.TOP or Gravity.LEFT
            }
        )
    }

}