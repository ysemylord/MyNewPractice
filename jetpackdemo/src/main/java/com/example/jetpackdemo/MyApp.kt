package com.example.jetpackdemo

import android.app.Application
import android.util.Log

class MyApp : Application() {

    companion object {
        lateinit var instance: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.i("Application", "create")
    }
}