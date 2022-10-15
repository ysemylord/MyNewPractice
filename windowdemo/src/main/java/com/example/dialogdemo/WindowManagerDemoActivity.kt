package com.example.dialogdemo

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class WindowManagerDemoActivity : AppCompatActivity() {

    private val customView by lazy {
        LayoutInflater.from(this).inflate(R.layout.view_to_window_layout, null)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        //supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window_manager_demo)


        this.display?.run {
            val point = Point()
            this.getRealSize(point)
            Log.i("DemoActivity", "Display ${point.x}:${point.y}")
        }

        val metrics = windowManager.currentWindowMetrics
        val bounds = metrics.bounds

        Log.i("DemoActivity", "WindowMetrics ${bounds.width()}:${bounds.height()}")

        Log.i("DemoActivity", "status bar ${getStatusBarHeight1(this)}")

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView
        ) { v, insets ->
            insets.getInsets(WindowInsetsCompat.Type.statusBars()).run {
                Log.i("window inset statusBars", "${toString()}")
            }
            insets
        }

        Log.i("DemoActivity", "navigation bar ${getNavigationBarHeight(this)}")

        Handler().postDelayed({
            val contentView = findViewById<View>(Window.ID_ANDROID_CONTENT)
            val contentViewHeight = contentView.height
            Log.i("DemoActivity", "contentViewHeight $contentViewHeight ")
            (contentView.parent.parent.parent as? ViewGroup).run {
                Log.i("DemoActivity screen_simple paddingTop", this?.paddingTop.toString())
                Log.i(
                    "DemoActivity screen_simple MarginLayoutParams",
                    (this?.layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin.toString()
                )
            }
        }, 1000)

    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun addOneWindowToTheScreen(view: View) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        Log.i("DemoActivity", "${view.layoutParams}")
        customView.setOnClickListener {
            windowManager.removeView(customView)
        }

        windowManager.addView(customView, WindowManager.LayoutParams().apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.START or Gravity.TOP

            x = 0
            y = 0
        })


    }

    private fun getStatusBarHeight1(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    private fun getStatusBarHeight2() {


        ViewCompat.getRootWindowInsets(window.decorView)?.run {
            getInsets(WindowInsetsCompat.Type.statusBars()).run {
                Log.i("window inset statusBars", "${toString()}")
            }
            getInsets(WindowInsetsCompat.Type.navigationBars()).run {
                Log.i("window inset navigationBars", "${toString()}")
            }
        }

    }

    private fun getNavigationBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        var height = 0
        if (resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId)
        }
        return height
    }

    fun addSystemWindowToTheScreen(view: View) {

        val windowManager =
            application.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        Log.i("DemoActivity", "${view.layoutParams}")
        customView.setOnClickListener {
            windowManager.removeView(customView)
        }

        windowManager.addView(customView, WindowManager.LayoutParams().apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.START or Gravity.TOP
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            x = 0
            y = 0
        })
    }

    fun getSystemHeight(view: View) {
        getStatusBarHeight2()
    }

}