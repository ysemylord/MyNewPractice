package com.kotlin_demo.coroutines_demo.base

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {

        val startTime = System.currentTimeMillis()
        val a = async {
            doSomethingUsefulOne()
        }
        val b = async {
            doSomethingUsefulTwo()
        }
        println("${a.await()} : ${b.await()} ")
        val endTime = System.currentTimeMillis()
        println("${endTime - startTime}")
    }
}


suspend fun doSomethingUsefulOne(): Int {
    delay(1000L)
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L)
    return 29
}