package com.example.testaidlapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * 服务端的Binder由Service提供
 */
class MyService : Service() {

    /**
     * 创建服务端的Binder
     */
    override fun onBind(intent: Intent): IBinder {
          return MyBinder()
    }

    class MyBinder:IMyAidlInterface.Stub(){
        override fun getInfo(s: String?): String {
           return s+"dd"
        }

    }
}