package com.example.myapplication.start_activity_demo.result_api

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.R

class NewStartActivityDemo2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_start_demo2)
        Log.i("NewStartActivityDemo2", "收到的数据: ${intent.getStringExtra("name")}")
    }

    fun back(view: android.view.View) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra("name", "mary")
        })
        finish()
    }
}