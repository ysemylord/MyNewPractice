package com.example.notificationdemoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun noti(view: View) {
       // NotificationUtil.test(this)
        val parent = window.decorView.parent
        Log.i("fdfdf",parent::class.java.name)
    }
}