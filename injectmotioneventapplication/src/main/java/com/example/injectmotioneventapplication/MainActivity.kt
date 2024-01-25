package com.example.injectmotioneventapplication

import android.app.Instrumentation
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectMotion()
    }
    
    fun injectMotion(){
        thread {
            Thread.sleep(5000)
            while (true) {
                val instrumentation = Instrumentation()
                val currentTimeMillis = System.currentTimeMillis()
                instrumentation.sendPointerSync(
                    MotionEvent.obtain(
                        currentTimeMillis,
                        currentTimeMillis,
                        MotionEvent.ACTION_DOWN,
                        534f,
                        327f,
                        0
                    )
                )

                instrumentation.sendPointerSync(
                    MotionEvent.obtain(
                        currentTimeMillis,
                        currentTimeMillis,
                        MotionEvent.ACTION_UP,
                        534f,
                        327f,
                        0
                    )
                )
                Thread.sleep(500)
            }
        }
    }
}