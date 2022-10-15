package ch03

import kotlinx.coroutines.*
import log
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.*

class Ch033 {


    class MyCoroutineContextElement1(val name: String = "") : AbstractCoroutineContextElement(Key) {
        companion object Key : CoroutineContext.Key<MyCoroutineContextElement1>

        fun hello(p: String) {
            "自己的协程上下文元素 $p".log()
        }
    }


    /**
     * 协程上下文
     */
    @Test
    fun coroutineContext() {
        suspend {
            coroutineContext[MyCoroutineContextElement1]?.hello("1")
            coroutineContext.hashCode().toString().log()
            "1"
        }.startCoroutine(object : Continuation<String> {
            override val context =
                EmptyCoroutineContext + CoroutineName("我的协程") +
                        CoroutineExceptionHandler { coroutineContext, throwable -> "啥也不干" } + MyCoroutineContextElement1()

            override fun resumeWith(result: Result<String>) {
                context[MyCoroutineContextElement1]?.hello("2")
                context.hashCode().toString().log()

            }

        })
        Thread.sleep(100000)


    }


    class LogInterceptor : ContinuationInterceptor {

        override val key = ContinuationInterceptor.Key

        override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> =
            object : Continuation<T> by continuation {

                override fun resumeWith(result: Result<T>) {
                    "before resume $result".log()
                    continuation.resumeWith(result)
                    "after resume $result".log()
                }

            }

    }



    suspend fun func1(): String {
        return suspendCoroutine<String> {
            thread {
                Thread.sleep(2000)
                it.resume("4")
            }
        }
    }

    @Test
    fun testInterceptor() {
        suspend {
            func1()
            func1()
            "1"
        }.startCoroutine(object : Continuation<String> {
            override val context = LogInterceptor()

            override fun resumeWith(result: Result<String>) {

            }

        })
        Thread.sleep(100000)
    }

    @Test
    fun testInterceptor2() {
        "1".log()
        suspend {
            "2".log()
            "2"
        }.startCoroutine(object : Continuation<String> {
            override val context = LogInterceptor()

            override fun resumeWith(result: Result<String>) {

            }

        })
        Thread.sleep(100000)
    }
}