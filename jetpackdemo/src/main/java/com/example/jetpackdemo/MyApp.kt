package com.example.jetpackdemo

import android.app.Application
import android.util.Log

class MyApp :Application(){
    override fun onCreate() {
        super.onCreate()
        Log.i("Application","create")
    }
}