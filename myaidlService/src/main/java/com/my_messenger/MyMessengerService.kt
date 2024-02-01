package com.my_messenger

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.Parcelable
import android.util.Log
import com.ryg.sayhi.aidl.ParcelString

class MyMessengerService : Service() {

    companion object{
        const val TAG="MyMessengerService"
    }

    private val messenger = Messenger(object:Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            msg.data.classLoader = classLoader
            val data = msg.data.getParcelable("data") as? ParcelString
            Log.i(TAG,"Messenger get message from client ${msg.toString()},data:$data")
            msg.replyTo.send(Message.obtain().also {
                 it.obj=ParcelString("service message")
                it.data.putParcelable("data",ParcelString("service data"))
                it.what = 2
                Log.i(TAG,"send message to client $it")
            })
        }
    })

    override fun onBind(intent: Intent): IBinder {
         return messenger.binder
    }
}