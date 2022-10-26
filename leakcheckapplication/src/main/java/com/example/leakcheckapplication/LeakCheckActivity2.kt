package com.example.leakcheckapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

object single {
    var activity: Any? = null
}

class LeakCheckActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        single.activity = this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_check2)
    }
}