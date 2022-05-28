package com.kotlin_coroutines

import org.jetbrains.annotations.TestOnly
import kotlin.concurrent.thread
import kotlin.coroutines.*

/**
 * 这个suspendMain函数，会创建一个协程
 * 后面隐式传递给挂起函数里面的协程体都是它的协程体
 */
/*
suspend fun main() {
    val res = suspendFunc02()
    print("result $res")
}
*/

/**
 * suspend fun main会隐式地创建一个协程，
 * 为了看起来更清晰，手动创建一个协程
 */

suspend fun main() {
    suspend {
        suspendCoroutine<Int> { continuation -> //suspendCoroutine 挂起协程
            thread {
                continuation.resumeWith(Result.success(100))//恢复协程
            }
        }

        suspendCoroutine<Int> { continuation -> //suspendCoroutine 挂起协程
            thread {
                continuation.resumeWith(Result.success(100))//恢复协程
            }
        }
    }.startCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println(Thread.currentThread())
            println(result)
        }
    })
}

class A2Snippet {

    fun fdf(){

    }
}


suspend fun suspendFunc01(): Int {
    return 2121
}

suspend fun suspendFunc02(): Int {
    return suspendCoroutine<Int> { continuation -> //suspendCoroutine 挂起协程
        thread {
            continuation.resumeWith(Result.success(100))//恢复协程
        }
    }
}