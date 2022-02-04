package com.kotlin_demo.coroutines_demo

import kotlinx.coroutines.*

fun main() {
    useWitchContext()
}

private fun useLaunch() {
    runBlocking(Dispatchers.Default) {
        launch(Dispatchers.IO) {
            delay(1000)
            println("在IO线程中干事")
        }
        println("在Default线程中干事")
    }
    Thread.sleep(2000)
}

private fun useWitchContext() {
    runBlocking(Dispatchers.Default) {
        withContext(Dispatchers.IO) {
            delay(1000)
            println("在IO线程中干事")
        }
        println("在Default线程中干事")
    }
    Thread.sleep(2000)
}