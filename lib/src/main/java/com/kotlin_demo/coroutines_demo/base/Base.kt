package com.kotlin_demo.coroutines_demo.base

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val job = launch{ // 在后台启动一个新的协程并继续
            delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
            println("${Thread.currentThread().name}: World!") // 在延迟后打印输出
        }
        println("${Thread.currentThread().name}: Hello,") // 协程已在等待时主线程还在继续
        job.join()
    }
}