package com.client_messenger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log
import androidx.annotation.RequiresApi
import com.ryg.sayhi.aidl.ParcelString

object UseMessenger {

    const val TAG ="UseMessenger"

    val messengerClientSend = Messenger(object :Handler(Looper.getMainLooper()){
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            msg.data.classLoader = ParcelString::class.java.classLoader
            val data = msg.data.getParcelable("data") as? ParcelString
            Log.i(TAG,"get response from service $msg, data: $data")
        }
    })

    fun bindService(context: Context){
        val intentService = Intent()
        val componentName = ComponentName(
            "com.example.myaidl.service",
            "com.my_messenger.MyMessengerService"
        )
        intentService.component = componentName
        //startService(intentService);
        //startService(intentService);
        Log.i(TAG,"bind service to get Messenger")
        context.bindService(intentService, object :ServiceConnection{
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                Log.i(TAG,"onServiceConnected")
                val messenger = Messenger(service)
                val obtain = Message.obtain()
                obtain.what = 1
                //obtain.obj =ParcelString("client message")
                obtain.data = Bundle().also { it.putParcelable("data",ParcelString("client message")) }
                obtain.replyTo = messengerClientSend
                Log.i(TAG,"messenger send message what: ${obtain.what}")
                messenger.send(obtain)
            }

            override fun onServiceDisconnected(name: ComponentName?) {

            }

        }, Context.BIND_AUTO_CREATE)
    }
}