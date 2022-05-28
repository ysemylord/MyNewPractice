package com.kotlin_coroutines

import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

/**
 * title 函数的挂起
 */


suspend fun main() {
    //将当前协程挂起
    val result = suspendCoroutine<Int> { coninutaion ->
        thread {
            Thread.sleep(2000)//模拟耗时任务
            coninutaion.resumeWith(Result.success(23))
        }
    }
    //打印结果
    print(result)
}