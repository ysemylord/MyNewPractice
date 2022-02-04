package com.kotlin_demo.coroutines_demo.base

import kotlinx.coroutines.*

fun main() {
    first()
    //second()
}

private fun second() {
    runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch {
            var nextPrintTime = startTime
            var i = 0
            while (i < 10 && isActive) { // 一个执行计算的循环，只是为了占用 CPU
                // 每秒打印消息两次
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("${Thread.currentThread().name} job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(300L) // 等待一段时间
        println("${Thread.currentThread().name} main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消一个作业并且等待它结束
        println("main: Now I can quit.")
    }
}


private fun first() {
    runBlocking {
        val job = launch(Dispatchers.IO) { // 在后台启动一个新的协程并继续
            try {
                repeat(1000) {
                    println("${Thread.currentThread().name} job:$it")

                    delay(10)

                }
            } catch (e: CancellationException) {

            } finally {
                println("exception")
            }
        }

        delay(50)
        job.cancelAndJoin()
        println("${Thread.currentThread().name} finish")
    }
}