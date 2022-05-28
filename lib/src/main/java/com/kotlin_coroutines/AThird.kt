package com.kotlin_coroutines

import java.util.concurrent.CompletableFuture

fun main() {
    println("1 ${Thread.currentThread()}")
    useCompletableFuture("url1")
    Thread.sleep(2000)
}

/**
 * 熟悉一下CompletableFuture
 */
private fun useCompletableFuture(url: String) {
    //supplyAsync 将任务添加进了线程池
    //返回一个CompletableFuture
    val completableFuture = CompletableFuture.supplyAsync {
        Bitmap(url)
    }
    //thenApply 转化结果
    //thenAccept 处理结果
    completableFuture
        .thenApply { bitmap ->
            println("2 ${Thread.currentThread()}")
            Bitmap(bitmap.url + " 1")
        }
        .thenAccept {
            println("3 ${Thread.currentThread()}")
        }
}

