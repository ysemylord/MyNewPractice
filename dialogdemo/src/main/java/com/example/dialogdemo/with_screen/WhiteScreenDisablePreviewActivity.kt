package com.example.dialogdemo.with_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dialogdemo.R

class WhiteScreenDisablePreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //模拟Activity启动较慢的情况
        val time = System.currentTimeMillis()
        while (System.currentTimeMillis()-time<3000){

        }
        setContentView(R.layout.activity_white_screen_disable_preview)
    }
}