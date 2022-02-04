package com.kotlin_demo.coroutines_demo

import kotlinx.coroutines.*

fun main() {
    //use1()

}

private fun use2() {
    runBlocking(Dispatchers.Default) {
        withContext(Dispatchers.IO) {
            println("在IO线程中干事")
        }
        println("在主线程中干事")
        withContext(Dispatchers.Default) {
            println("在IO线程中干事")
        }
        println("在主线程中干事")
    }
    Thread.sleep(2000)
}

private fun use1() {
    runBlocking(Dispatchers.Default) {
        launch(Dispatchers.IO) {
            println("在IO线程中干事")
            launch(Dispatchers.Default) {
                println("在主线程中干事")
                launch(Dispatchers.IO) {
                    println("在IO线程中干事")
                    launch(Dispatchers.Default) {
                        println("在主线程中干事")
                    }
                }
            }
        }
    }
    Thread.sleep(2000)
}