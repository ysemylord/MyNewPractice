package com.example.customview

import android.app.Application
import com.example.customview.backround_performance.BgController

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        BgController.init(this)
    }
}