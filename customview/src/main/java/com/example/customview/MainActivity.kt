package com.example.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.customview.MyLayoutInflaterFactory.MyLayoutInflaterFactory
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    class HoldWeak(val weakR:WeakReference<AppCompatActivity>){
        fun start(){
            thread {
                Thread.sleep(10000)
                Log.i("MainActivity","${weakR.get()}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HoldWeak(WeakReference(this@MainActivity)).start()
        thread {
            Thread.sleep(10000)
            findViewById<View>(R.id.root).let {
                Log.i("MainActivity","$it")
                Log.i("MainActivity","${MainActivity@this}")
            }
        }
    }
}