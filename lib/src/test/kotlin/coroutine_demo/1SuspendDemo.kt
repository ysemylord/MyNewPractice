package coroutine_demo

import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.*

/**
 * title 协程挂起
 */
class `1SuspendDemo` {
    /**
     * 1. 创建一个协程，
     * 2.挂起协程
     * 3.子线程
     */
    @Test
    fun test1() {
        suspend {
            val res1 = suspendCoroutine<Int> { continuation -> //suspendCoroutine 挂起协程
                println(Thread.currentThread())
                thread {
                    val res = 100
                    if (res >= 10) {
                        continuation.resumeWith(Result.success(100))//恢复协程
                    } else {
                        continuation.resumeWithException(Exception("error number"))
                    }
                }
            }

            val res2 = suspendCoroutine<Int> { continuation -> //suspendCoroutine 挂起协程
                println(Thread.currentThread())
                thread {
                    val res = 100
                    if (res >= 10) {
                        continuation.resumeWith(Result.success(200))//恢复协程
                    } else {
                        continuation.resumeWithException(Exception("error number"))
                    }
                }
            }
            res1 + res2
        }.startCoroutine(object : Continuation<Int> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                println(Thread.currentThread())
                println(result)
            }
        })

        Thread.sleep(5000)
    }

    @Test
    fun test2() {
        suspend {
            val startTime = System.currentTimeMillis()
            val a = suspendFunc02()
            val b = suspendFunc02()
            val endTime = System.currentTimeMillis()
            println("cost time: ${endTime - startTime}")
            a + b
        }.startCoroutine(object : Continuation<Int> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                println("resume")
                println(result)
            }
        })
        Thread.sleep(5000)
    }


    private suspend fun suspendFunc02() = suspendCoroutine<Int> { continuation ->
        thread {
            Thread.sleep(2000)
            continuation.resumeWith(Result.success(5)) // ... ①
        }
    }
}
