package com.example.testaidlapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindService(Intent(this, MyService::class.java), object : ServiceConnection {
            //这里返回的IBinder是Binder的远程代理BinderProxy
            override fun onServiceConnected(name: ComponentName?, binderPorxy: IBinder?) {
                //这里通过BinderProxy就可以和服务端通信了，不过将其包装为Stub.Proxy,
                //隐藏具体实现，用户只需要调用封装好具体的方法就行了
                val clientMyAidlInterface = IMyAidlInterface.Stub.asInterface(binderPorxy)
                val value = clientMyAidlInterface.getInfo("dd")
                runOnUiThread {
                    findViewById<TextView>(R.id.text_view).text=value
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {

            }

        }, Context.BIND_AUTO_CREATE)

    }
}