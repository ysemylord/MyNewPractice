package com.gson_demo

import com.google.gson.Gson

fun main() {
    val gson = Gson()
    val result1 = gson.fromJson("",LicenseBean::class.java)
    println(result1)

    val result2 = gson.fromJson("{}",LicenseBean::class.java)
    println(result2)
    val fd:String = result2.key
    fd.length
    println(result2.key)
}