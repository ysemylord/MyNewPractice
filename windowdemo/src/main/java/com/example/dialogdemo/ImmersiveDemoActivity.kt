package com.example.dialogdemo

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Choreographer
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE

/**
 * 设置沉浸式
 */
class ImmersiveDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //app 全屏效果
        /*     window.decorView.systemUiVisibility =
                 View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                         View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or //应用内容扩展到状态栏
                         View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  or//应用内容扩展到导航栏
                         View.SYSTEM_UI_FLAG_FULLSCREEN or // 隐藏状态栏
                         View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航栏
                         View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY //沉浸模式，用户可以交互的界面。同时，用户上下拉系统栏时，会自动显示系统栏 然后隐藏系统栏*/



        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_window_inset_demo)
        val view = findViewById<View>(R.id.root_view)
        Handler().post {
            val height = view.height
            val measuredHeight = view.measuredHeight
            Log.i("post get Height1","$height $measuredHeight")
        }


        Handler().postDelayed({
            val height = view.height
            val measuredHeight = view.measuredHeight
            Log.i("post get Height2","$height $measuredHeight")
        },20)


        view.post {
            val height = view.height
            val measuredHeight = view.measuredHeight
            Log.i("post get Height3","$height $measuredHeight")

        }

        //3
        immersiveThree()


        Looper.getMainLooper().setMessageLogging {
            Log.i("fdf", "$it")
        }

        window.attributes.type


    }

    val test = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            Log.i("fdfdfdf","dfdf")
            Choreographer.getInstance().postFrameCallback(this)
        }

    }


    //改变系统栏颜色
    @RequiresApi(Build.VERSION_CODES.M)
    private fun immersiveOne() {
        window.statusBarColor = getColor(android.R.color.holo_purple)
        window.navigationBarColor = getColor(android.R.color.holo_purple)
    }

    //让内容扩展到系统栏，系统栏改为透明色
    private fun imersiveTwo() {

        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        /*
          过时了
          window.decorView.systemUiVisibility =
               View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                       View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or //应用内容扩展到状态栏
                       View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or//应用内容扩展到导航栏
                       */

        //替换SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN和SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        WindowCompat.setDecorFitsSystemWindows(window, false)


    }

    //让内容扩展到系统栏，并隐藏系统栏
    private fun immersiveThree() {
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        /*window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or //应用内容扩展到状态栏
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or//应用内容扩展到导航栏
                    View.SYSTEM_UI_FLAG_FULLSCREEN or // 隐藏状态栏
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航栏
        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY //沉浸模式，用户可以交互的界面。同时，用户上下拉系统栏时，会自动隐藏系统栏*/

        //内容扩展到状态栏
        WindowCompat.setDecorFitsSystemWindows(window, false)



        ViewCompat.getWindowInsetsController(window.decorView)?.run {
            this.hide(WindowInsetsCompat.Type.statusBars())
            this.hide(WindowInsetsCompat.Type.navigationBars())
            //this.systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE //沉浸模式-黏粘性
            this.systemBarsBehavior = BEHAVIOR_SHOW_BARS_BY_SWIPE  //沉浸模式-非黏粘性

        }

    }
}