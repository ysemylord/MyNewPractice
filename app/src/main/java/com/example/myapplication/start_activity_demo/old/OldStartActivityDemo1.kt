package com.example.myapplication.start_activity_demo.old

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.R

class OldStartActivityDemo1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_demo1)
    }

    fun onMyClick(view: android.view.View) {
        val intent = Intent(this@OldStartActivityDemo1, OldStartActivityDemo2::class.java)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == 2) {
            Log.i("OldStartActivityDemo1", data?.getStringExtra("name") ?: "")
        }
    }
}