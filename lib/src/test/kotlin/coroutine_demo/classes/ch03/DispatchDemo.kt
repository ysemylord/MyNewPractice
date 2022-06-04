package coroutine_demo.classes.ch03

import coroutine_demo.classes.ch04.lua.DispatchInterceptor
import org.junit.Test
import kotlin.coroutines.*

class DispatchDemo {

    /**
     * 调度权限
     * A->B->A->C
     */
    @Test
    fun test1() {


        var bTempContinuation: Continuation<Unit>? = null
        var cTempContinuation: Continuation<Unit>? = null
        var aTempContinuation: Continuation<Unit>? = null


        val completionA = object : Continuation<Unit> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Unit>) {
                println("主协程结束")
            }
        }

        val completionB = object : Continuation<Unit> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext + DispatchInterceptor()

            override fun resumeWith(result: Result<Unit>) {
                aTempContinuation?.resume(Unit)
            }

        }

        val completionC = object : Continuation<Unit> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext + DispatchInterceptor()

            override fun resumeWith(result: Result<Unit>) {
                println("消费者协程结束")

                aTempContinuation?.resume(Unit)
            }
        }

        val bCoroutine = suspend {
            println("bCoroutine 1")
            suspendCoroutine<Unit> {
                aTempContinuation?.resume(Unit)
            }
        }.createCoroutine(completionB)

        val cCoroutine = suspend {
            println("cCoroutine 1")
            suspendCoroutine<Unit> {
                aTempContinuation?.resume(Unit)
            }
        }.createCoroutine(completionC)

        val aCoroutine = suspend {

            suspendCoroutine<Unit> {
                aTempContinuation = it
                bCoroutine.resume(Unit)
            }

            suspendCoroutine<Unit> {
                aTempContinuation = it
                cCoroutine.resume(Unit)
            }
            Unit
        }.createCoroutine(completionA)

        aCoroutine.resume(Unit)

        Thread.sleep(100000)

    }

    /**
     * 错误示例
     */
    @Test
    fun test2() {


        var bTempContinuation: Continuation<Unit>? = null
        var cTempContinuation: Continuation<Unit>? = null
        var aTempContinuation: Continuation<Unit>? = null

        val completionA = object : Continuation<Unit> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Unit>) {
                println("主协程结束")
            }
        }

        val completionB = object : Continuation<Unit> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext + DispatchInterceptor()

            override fun resumeWith(result: Result<Unit>) {
                aTempContinuation?.resume(Unit)
            }

        }

        val completionC = object : Continuation<Unit> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext + DispatchInterceptor()

            override fun resumeWith(result: Result<Unit>) {
                aTempContinuation?.resume(Unit)
                println("消费者协程结束")
            }
        }


        val cCoroutine = suspend {
            println("cCoroutine 1")
            suspendCoroutine<Unit> {
                cTempContinuation = it
                bTempContinuation?.resume(Unit)
            }
            println("cCoroutine 2")
        }.createCoroutine(completionC)

        val bCoroutine = suspend {
            println("bCoroutine 1")
            suspendCoroutine<Unit> {
                bTempContinuation = it
                //这里如果我们想把调取权限直接给C
                cCoroutine.resume(Unit)
            }
            println("bCoroutine 2")
            suspendCoroutine<Unit> {
                bTempContinuation = it
                cTempContinuation?.resume(Unit)
            }

            println("bCoroutine 2")
        }.createCoroutine(completionB)


        val aCoroutine = suspend {
            println("aCoroutine1")
            suspendCoroutine<Unit> {
                aTempContinuation = it
                bCoroutine.resume(Unit)
            }
            println("aCoroutine2")

        }.createCoroutine(completionA)

        aCoroutine.resume(Unit)

        Thread.sleep(100000)

    }
}