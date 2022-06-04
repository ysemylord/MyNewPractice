package coroutine_demo.classes.ch04.lua

import java.util.concurrent.Executors
import kotlin.coroutines.*

/**
 * 简单模仿一下Lua的API风格(非对称协程)
 * 主要是调度权限的流转
 * https://drive.google.com/file/d/134-ZCyYYkJuCO1dY_veeGIs5DF5lIycv/view?usp=sharing
 */

class DispatchInterceptor : ContinuationInterceptor {
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return DispatchContinuation(continuation)
    }
}

class DispatchContinuation<T>(private val continuation: Continuation<T>) : Continuation<T> by continuation {
    private val executor = Executors.newSingleThreadExecutor()

    override fun resumeWith(result: Result<T>) {
        executor.submit {
            continuation.resumeWith(result)
        }
    }

}

fun main() {

    var produceNum = 0
    var producerTempContinuation: Continuation<Unit>? = null
    var consumerTempContinuation: Continuation<Unit>? = null
    var mainTempContinuation: Continuation<Unit>? = null
    var producerDead = false
    var consumerDead = false
    producerTempContinuation = suspend {
        for (num in 0..3) {
            suspendCoroutine<Unit> {
                produceNum = 5 + num
                println("${Thread.currentThread()} 生产 $produceNum")
                producerTempContinuation = it
                mainTempContinuation?.resume(Unit)
            }
        }
        200
    }.createCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext + DispatchInterceptor()

        override fun resumeWith(result: Result<Int>) {
            println("生产者协程结束")
            producerDead = true
            mainTempContinuation?.resume(Unit)
        }

    })

    consumerTempContinuation = suspend {
        for (num in 0..3) {
            suspendCoroutine<Unit> {
                consumerTempContinuation = it
                println("${Thread.currentThread()} 消耗 $produceNum")
                mainTempContinuation?.resume(Unit)
            }
        }
    }.createCoroutine(object : Continuation<Unit> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext + DispatchInterceptor()

        override fun resumeWith(result: Result<Unit>) {
            println("消费者协程结束")
            consumerDead = true
            mainTempContinuation?.resume(Unit)
        }
    })

    suspend {
        while (!producerDead && !consumerDead) {
            suspendCoroutine<Unit> {
                mainTempContinuation = it
                producerTempContinuation?.resume(Unit)
            }

            suspendCoroutine<Unit> {
                mainTempContinuation = it
                consumerTempContinuation?.resume(Unit)
            }
        }
        Unit
    }.startCoroutine(object : Continuation<Unit> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Unit>) {
            println("主协程结束")
        }
    })

    Thread.sleep(100000)

}