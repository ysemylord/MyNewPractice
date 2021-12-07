package com.example.myapplication.koin_test

import android.util.Log

class UserData {
    var userName: String? = null
    var age: Int? = null
    fun info() {
        Log.i("koin","用户名:" + userName + "////年龄:" + age)
    }
}
