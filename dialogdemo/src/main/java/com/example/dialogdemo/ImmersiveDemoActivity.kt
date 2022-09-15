package com.example.dialogdemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

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
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY //沉浸模式，用户可以交互的界面。同时，用户上下拉系统栏时，会自动隐藏系统栏*/



        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_window_inset_demo)


        //3
        immersiveThree()

    }

    private fun immersiveThree() {
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or //应用内容扩展到状态栏
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or//应用内容扩展到导航栏
                    View.SYSTEM_UI_FLAG_FULLSCREEN or // 隐藏状态栏
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航栏
        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY //沉浸模式，用户可以交互的界面。同时，用户上下拉系统栏时，会自动隐藏系统栏
    }

    private fun imersiveTwo() {
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or //应用内容扩展到状态栏
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or//应用内容扩展到导航栏
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY //沉浸模式，用户可以交互的界面。同时，用户上下拉系统栏时，会自动隐藏系统栏
    }

    private fun ImmersiveOne() {
        window.statusBarColor = getColor(android.R.color.holo_purple)
        window.navigationBarColor = getColor(android.R.color.holo_purple)
    }
}