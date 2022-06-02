package coroutine_demo.classes


import kotlinx.coroutines.delay
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.random.Random


/**
 * title 协程的要素
 */


class Class4 {

    suspend fun getName1(): String {
        return "jack"
    }

    suspend fun getName2(): String {
        return suspendCoroutine<String> { continuation ->
            continuation.resumeWith(Result.success("jack"))
        }
    }


    suspend fun getName3(): String {
        return suspendCoroutine<String> { continuation ->
            thread {
                continuation.resumeWith(Result.success("jack"))
            }

        }
    }

    /**
     * 结合笔记理解协程的挂起和恢复
     */
    @Test
    fun continuationDispatch() {
        suspend {
            println(1)
            println(returnSuspend1())
            println(2)
            println(returnSuspend2())
            println(3)
            println(returnImmediately())
            println(4)
            delay(1000)
            1
        }.startCoroutine(object : Continuation<Int> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                println("结果 $result")
            }
        })
        Thread.sleep(10000)
    }


    private suspend fun returnImmediately(): String {
        return "jack"
    }

    private suspend fun returnSuspend1(): String {
        return suspendCoroutine<String> { continuation ->
            continuation.resumeWith(Result.success("davide"))
        }
    }

    private suspend fun returnSuspend2(): String {
        return suspendCoroutine<String> { continuation ->
            thread {
                continuation.resumeWith(Result.success("jack"))
            }
        }
    }


    /**
     * 简单的挂起，恢复
     */
    @Test
    fun continuationDispatch2() {
        suspend {
            val res1 = suspendCoroutine<Int> { continuation ->
                continuation.resume(1)
            }
            val res2 = suspendCoroutine<Int> { continuation ->
                thread {
                    continuation.resume(2)
                }
            }
            res1 + res2
        }.startCoroutine(object : Continuation<Int> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                println("结果 $result")
            }
        })
        Thread.sleep(10000)
    }

    /**
     * 挂起，恢复的轮转
     */
    @Test
    fun continuationDispatchRotation() {
        val start = 10
        suspend {
            for (i in 0..10) {
                val result = suspendCoroutine<Int> {
                    it.resume(start + i)
                }
                println(result)
            }
        }.startCoroutine(object : Continuation<Unit> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Unit>) {
                println("结果 $result")
            }
        })
        Thread.sleep(10000)
    }

    @Test
    fun checkContinuation() {
        val continuation = suspend {
            val res1 = suspendCoroutine<Int> { continuation ->
                println("continuation1 ${continuation.hashCode()}")
                continuation.resume(1)
            }
            val res2 = suspendCoroutine<Int> { continuation ->
                println("continuation2 ${continuation.hashCode()}")
                thread {
                    continuation.resume(2)
                }
            }
            res1 + res2
        }.createCoroutine(object : Continuation<Int> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                println("结果 $result")
            }
        })
        println("continuation3 ${continuation.hashCode()}")
        continuation.resume(Unit)
        Thread.sleep(10000)
    }

    @Test
    fun continuationDispatch3() {
        var value = 0
        var tempContinuation: Continuation<Unit>? = null
        val continuation = suspend {
            while (true) {
                suspendCoroutine<Unit> { continuation ->
                    tempContinuation = continuation
                    val randomNum = Random.nextInt()
                    value = randomNum
                }
            }
            Unit
        }.createCoroutine(object : Continuation<Unit> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Unit>) {
                println("结果 $result")
            }
        })


        continuation.resume(Unit)//让协程执行
        while (true) {
            Thread.sleep(2000)
            tempContinuation?.resume(Unit)
            println(value)
        }
        Thread.sleep(1000000)
    }


    class MyIterator(private val data: List<String>) : Iterator<String> {
        val continuation: Continuation<Unit>

        init {
            continuation = suspend {

            }.createCoroutine(object : Continuation<Unit> {
                override val context: CoroutineContext
                    get() = EmptyCoroutineContext

                override fun resumeWith(result: Result<Unit>) {
                    println("协程执行完毕")
                }

            })
        }

        var index = 0
        override fun hasNext(): Boolean = index < data.size

        override fun next(): String {
            if (hasNext()) {
                return data[index++]
            }
            throw  NoSuchElementException("only " + data.size + " elements");
        }
    }

    @Test
    fun simpleGenerator() {

    }
}


