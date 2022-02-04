package com.example.myapplication.start_activity_demo.old

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R

class OldStartActivityDemo2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_old_start_demo2)
    }

    fun finishPage(view: android.view.View) {
        setResult(2, Intent().apply {
            putExtra("name", "jack")
        })
        finish()
    }
}