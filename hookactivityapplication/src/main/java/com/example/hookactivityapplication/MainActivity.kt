package com.example.hookactivityapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.Parcelable
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.Proxy

/**
 * Hook android 9.0
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.text_view).setOnClickListener {
            hookActivity()
            hookHandler()
            startActivity(Intent(this, TargetActivity::class.java))
        }
    }
}

/**
 *->Activity.start()
 *->Instrumentation.execStartActivity()
 * ->ActivityManager.startActivity()
 * ->IActivityManagerSingleton.mInstance.startActivity
 * 这个mInstance就是AMS的远程代理。
 * 我们要做的就是hook这个mInstance
 *
 * android 8.0
 * hook ActivityManger.java中的
 * IActivityManagerSingleton的
 * mInstance
 */
fun hookActivity() {

    //通过反射拿到mInstance
    //创建mInstance的代理
    //将mInstance设置为创建的代理

    val clazz = Class.forName("android.app.ActivityManager")

    val singletonField = clazz.getDeclaredField("IActivityManagerSingleton")
    singletonField.isAccessible = true
    var singletonObj = singletonField.get(null)

    val singleton = Class.forName("android.util.Singleton")
    val instanceField = singleton.getDeclaredField("mInstance").apply {
        isAccessible = true
    }
    val oldInstanceObj = instanceField.get(singletonObj)//这是AMS的远程代理，实现android.app.IActivityManager接口

    val instanceProxy = Proxy.newProxyInstance(
        Thread.currentThread().contextClassLoader,
        arrayOf(Class.forName("android.app.IActivityManager"))
    ) { proxy, method, args ->

        if ("startActivity" == method.name) {
            args.forEachIndexed { index, it ->
                if (it is Intent && it.component?.className == TargetActivity::class.java.name) {
                    args[index] = Intent().apply {
                        component = ComponentName(
                            "com.example.hookactivityapplication",
                            StubActivity::class.java.name
                        )
                        putExtra("extra", it)
                    }
                    return@forEachIndexed
                }
            }
        }

        method.invoke(oldInstanceObj, *args)
    }

    instanceField.set(singletonObj, instanceProxy)

}

fun hookHandler() {
    val activityThreadClazz = Class.forName("android.app.ActivityThread")
    val sCurrentActivityThreadField =
        activityThreadClazz.getDeclaredField("sCurrentActivityThread").apply { isAccessible = true }
    val mHField = activityThreadClazz.getDeclaredField("mH").apply {
        isAccessible = true
    }
    val sCurrentActivityThreadObj = sCurrentActivityThreadField.get(null)
    val mHObject = mHField.get(sCurrentActivityThreadObj)
    val mHHandler = mHObject as Handler
    val handlerClazz = Handler::class.java
    val mCallBackField = handlerClazz.getDeclaredField("mCallback").apply { isAccessible = true }
    mCallBackField.set(mHHandler, object : Handler.Callback {
        override fun handleMessage(msg0: Message): Boolean {

            if (msg0.what == 159) {//EXECUTE_TRANSACTION
                //msg.obj为ClientTransaction
                val clientTransaction =msg0.obj
                val clientTransactionClazz =
                    Class.forName("android.app.servertransaction.ClientTransaction")
                val mActivityCallbacksField =
                    (clientTransactionClazz.getDeclaredField("mActivityCallbacks")).apply {
                        isAccessible = true
                    }
                val mActivityCallbacks = mActivityCallbacksField.get(clientTransaction) as List<Any>
                if (mActivityCallbacks.size > 0) {
                    if (mActivityCallbacks[0]::class.java.canonicalName == ("android.app.servertransaction.LaunchActivityItem")) {
                        //启动Activity的操作
                        val launchActivityItem = mActivityCallbacks[0]
                        val launchActivityItemClazz =
                            Class.forName("android.app.servertransaction.LaunchActivityItem")
                        val mIntentField = launchActivityItemClazz.getDeclaredField("mIntent")
                            .apply { isAccessible = true }
                        val oldIntent = mIntentField.get(launchActivityItem) as Intent
                        mIntentField.set(launchActivityItem, Intent().apply {
                            component = ComponentName(
                                "com.example.hookactivityapplication",
                                TargetActivity::class.java.name
                            )
                            putExtras(oldIntent)
                        })
                    }
                }

            }
            mHHandler.handleMessage(msg0)//使用mH实现的sendMessage方法处理
            return true
        }

    })

}