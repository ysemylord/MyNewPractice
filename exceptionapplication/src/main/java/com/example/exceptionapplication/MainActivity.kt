package com.example.exceptionapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.InvocationTargetException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun click(view: View) {

        /*  Observable.just("1")
              .subscribeOn(Schedulers.io())
              .subscribe {
                  try {
                      val clazz = MainActivity::class.java
                      val method = clazz.getDeclaredMethod("myClick")
                      method.invoke(this)
                  }catch (e: InvocationTargetException){
                      e.printStackTrace()
                  }

              }*/
    }

    fun myClick() {
        TestException().method()
    }
}


