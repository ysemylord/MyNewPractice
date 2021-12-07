package com.example.networkapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class NetworkActivity : AppCompatActivity() {
    lateinit var myViewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
        RetrofitTest.testWithRxJava()
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        myViewModel.name.observe(this) {
            findViewById<Button>(R.id.btn).text = it
        }
    }

    fun request(view: android.view.View) {
        RetrofitTest.testWithRxJava()
    }
}