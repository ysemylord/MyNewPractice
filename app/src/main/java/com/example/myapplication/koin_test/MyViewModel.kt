package com.example.myapplication.koin_test

import android.util.Log
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    var NumData :Int = 0
    override fun onCleared() {
        super.onCleared()
        Log.i("MyViewModel","调用了销毁方法")
    }
}