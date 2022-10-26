package com.example.leakcheckapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle



class LeakCheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_leak_check)
        startActivity(Intent(this, LeakCheckActivity2::class.java))
    }
}