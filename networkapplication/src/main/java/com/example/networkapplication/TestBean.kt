package com.example.networkapplication

class TestBean<T>(var t: T) {


    @JvmName("setT1")
    fun setT(v: T) {
        t = v
    }
}