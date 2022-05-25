package com.example.servicedemoapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlin.concurrent.thread

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        thread {
            while(true) {
                Thread.sleep(1000)
                Log.i("MyService", "1")
            }
        }
        super.onCreate()
    }
}