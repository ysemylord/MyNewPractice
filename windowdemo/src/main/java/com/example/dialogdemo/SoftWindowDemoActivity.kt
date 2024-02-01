package com.example.dialogdemo

import android.content.Context
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class SoftWindowDemoActivity : AppCompatActivity() {

    private val customView by lazy {
        LayoutInflater.from(this).inflate(R.layout.view_to_window_layout, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soft_window_demo)
    }

    fun show(view: View) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        Log.i("DemoActivity", "${view.layoutParams}")
        customView.setOnClickListener {
            windowManager.removeView(customView)
        }
        customView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        )
      /*  val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,  // 宽度自适应
            WindowManager.LayoutParams.WRAP_CONTENT,  // 高度自适应
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,  // 设置窗口类型为悬浮窗
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE // 设置窗口不获取焦点
                    //or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS // 可以超出屏幕限制
                    or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN // 在屏幕内绘制视图
                   // or WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR // 与装饰视图重叠
                   // or WindowManager.LayoutParams.FLAG_FULLSCREEN // 全屏模式，与状态栏重叠
                   // or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS // 状态栏透明
            or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
            ,
            PixelFormat.TRANSLUCENT // 设置窗口透明
        )*/
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,  // 宽度自适应
            WindowManager.LayoutParams.WRAP_CONTENT,  // 高度自适应
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,  // 设置窗口类型为悬浮窗
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE // 设置窗口不获取焦点
                    or WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                    or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN // 在屏幕内绘制视图
                    or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
            ,
            PixelFormat.TRANSLUCENT // 设置窗口透明
        )
        layoutParams.setFitInsetsTypes(0 /* types */)

        windowManager.addView(customView, layoutParams.apply {
            //type = 2031
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.START or Gravity.TOP

            x = 0
            y = 0
        })
    }
}