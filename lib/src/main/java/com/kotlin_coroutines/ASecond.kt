package com.kotlin_coroutines

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

private val ioExecutor = Executors.newCachedThreadPool()

//使用Future去简化异步任务的逻辑
fun main() {
    asyncFutureBitmap()
}

private fun asyncFutureBitmap():List<Bitmap> {
    val urls = listOf<String>("url1", "url2", "url3")
    val bitmaps = urls.map {
        bitmapFuture(it)
    }.map {
        it.get()
    }
    print(bitmaps.toString())
    return bitmaps
}

private fun bitmapFuture(url: String): Future<Bitmap> {
    return ioExecutor.submit(Callable {
        Thread.sleep(1000)
        Bitmap(url)
    })
}