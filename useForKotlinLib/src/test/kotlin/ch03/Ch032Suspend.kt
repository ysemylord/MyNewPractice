package ch03

import log
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

/**
 * 协程挂起
 * 挂起函数并不一定会使协程挂起，只有挂起函数内部发生异步调用时，才会使协程挂起
 */

private suspend fun func0(): String {
    return suspendCoroutine<String> { continuation ->

            continuation.resume("2")

    }
}


private suspend fun func1(): String {
    return suspendCoroutine<String> { continuation ->
        thread {
            continuation.resume("2")
        }
    }
}

private suspend fun func2(): String {
    return suspendCoroutine<String> { continuation ->
        //干了一堆事，但是没调continuation.resume
    }
}



fun main() {
    val continuation = suspend {
        "1".log()
        val result = func0()
        result.log()
        3
    }.createCoroutine(Ch031.MyCompletion())
    continuation.resume(Unit)
}

