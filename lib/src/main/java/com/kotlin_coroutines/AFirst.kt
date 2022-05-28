package com.kotlin_coroutines

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

//异步代码对复杂分支的处理
//例子：循环url数组，得到Bitmap数组

fun main() {
    异步操作()
}

//通过url数组返回一个bitmap数组
private fun 异步操作(): List<Bitmap> {
    val urls = listOf<String>("url1", "url2", "url3")
    val map = urls.map { it to Bitmap("empty") }.toMap(ConcurrentHashMap<String, Bitmap>())
    val countDownLatch = CountDownLatch(urls.size)
    urls.map { url ->
        asyncBitmap(url) { bitmap ->
            map[url] = bitmap
            countDownLatch.countDown()
        }
    }
    countDownLatch.await()
    val bitmaps = map.values.toList()
    print(bitmaps.toString())
    return bitmaps
}

private fun asyncBitmap(url: String, onSuccess: (Bitmap) -> Unit) {
    thread {
        Thread.sleep(4000)//延迟1秒(模拟网络请求)
        Bitmap(url).also {
            onSuccess(it)
        }
    }
}

//先看看同步操作
//通过url数组返回一个bitmap数组
private fun 同步操作(): List<Bitmap> {
    val urls = listOf<String>("url1", "url2", "url3")
    val bitmaps = urls.map { syncBitmap(it) }
    print(bitmaps.toString())
    return bitmaps
}


private fun syncBitmap(url: String): Bitmap = Bitmap(url)

data class Bitmap(val url: String)


