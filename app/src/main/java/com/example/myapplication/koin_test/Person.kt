package com.example.myapplication.koin_test

import android.util.Log

interface Animation

class Person : Animation {
    fun speak() {
        Log.i("Koin", "武汉加油,中国加油")
    }
}