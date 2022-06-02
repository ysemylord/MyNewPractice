package com.example.networkapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class NetworkActivity : AppCompatActivity() {
    lateinit var myViewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        myViewModel.name.observe(this) {
            findViewById<Button>(R.id.btn).text = it
        }
    }

    fun requestWithRxjava(view: android.view.View) {
        RetrofitTest.testWithRxJava()
    }

    fun requestWithCall(view: View) {
        RetrofitTest.testRetrofit()
    }

    fun requestWithCoroutine(view: View) {
        RetrofitTest.testWithCoroutineOne()
    }

    fun requestWithCoroutine2(view: View){
        lifecycleScope.launch {
            RetrofitTest.testWithCoroutineTwo()
        }
    }
}