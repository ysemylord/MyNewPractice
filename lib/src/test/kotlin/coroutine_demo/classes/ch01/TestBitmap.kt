package coroutine_demo.classes.ch01

import kotlin.concurrent.thread

class TestBitmap(val url: String)

fun syncBitmap(url: String) = TestBitmap(url)

fun asyncBitmap(url: String, callBack: (bitmap: TestBitmap) -> Unit) {
    thread {
        Thread.sleep(1000)
        //需要使用回调来接收异步程序的结果
        callBack(TestBitmap(url))
    }
}
