package coroutine_demo.classes.ch04.lua

import java.util.concurrent.Executors
import kotlin.coroutines.*

/**
 * 简单模仿一下Lua的API风格(非对称协程)
 * 相对于SimpleDispatch.kt，增强了对挂起点结果获取的认知
 * https://drive.google.com/file/d/134-ZCyYYkJuCO1dY_veeGIs5DF5lIycv/view?usp=sharing
 */

fun main() {

    var producerTempContinuation: Continuation<Unit>? = null
    var consumerTempContinuation: Continuation<Int>? = null
    var mainTempContinuation: Continuation<Int>? = null

    var producerDead = false
    var consumerDead = false
    val producerCoroutine = suspend {
        for (num in 0..3) {
            suspendCoroutine<Unit> {
                val produceNum = 5 + num
                println("生产 $produceNum")
                producerTempContinuation = it
                mainTempContinuation?.resume(produceNum)
            }
        }
        200
    }.createCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext + DispatchInterceptor()

        override fun resumeWith(result: Result<Int>) {
            println("生产者协程结束")
            producerDead = true
            mainTempContinuation?.resume(-1)
        }

    })

    val consumerCoroutine = suspend {
        for (num in 0..3) {
            val produceNum = suspendCoroutine<Int> {
                consumerTempContinuation = it
                mainTempContinuation?.resume(-1)
            }
            println("消耗 $produceNum")
        }
    }.createCoroutine(object : Continuation<Unit> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext + DispatchInterceptor()

        override fun resumeWith(result: Result<Unit>) {
            println("消费者协程结束")
            consumerDead = true
            mainTempContinuation?.resume(-1)
        }
    })

    suspend {

        var producerNum = suspendCoroutine<Int> {
            mainTempContinuation = it
            producerCoroutine.resume(Unit)
        }

        suspendCoroutine<Int> {
            mainTempContinuation = it
            consumerCoroutine.resume(Unit)
        }

        while (!producerDead && !consumerDead) {

            suspendCoroutine<Int> {
                mainTempContinuation = it
                consumerTempContinuation?.resume(producerNum)
            }

            producerNum = suspendCoroutine<Int> {
                mainTempContinuation = it
                producerTempContinuation?.resume(Unit)
            }
        }

    }.startCoroutine(object : Continuation<Unit> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Unit>) {
            println("主协程结束")
        }
    })

    Thread.sleep(100000)

}