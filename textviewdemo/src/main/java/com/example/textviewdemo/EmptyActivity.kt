package com.example.textviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class EmptyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)


    }



    fun click(view: View) {
        GlobalScope.launch/*(CoroutineExceptionHandler { _, _-> Log.i("fdfd","发生异常") })*/ {
            val i = 1 / 0
        }
        Log.i("EmptyActivity","click")
    }
}