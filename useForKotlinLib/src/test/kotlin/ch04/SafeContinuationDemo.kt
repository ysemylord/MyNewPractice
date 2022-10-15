package ch04

import org.junit.Test
import kotlin.coroutines.*

class SafeContinuationDemo {
    @Test
    fun one() {

        var firstContinuation: Continuation<Unit>? = null
        var secContinuation: Continuation<Unit>? = null
        var thirdContinuation: Continuation<Unit>? = null
        var fourthContinuation: Continuation<Unit>? = null
        var continuation = suspend {

            println("协程干事1")
            suspendCoroutine<Unit> {
                firstContinuation = it
                println("在第1个挂起点挂起")
            }

            println("协程干事2")

            suspendCoroutine<Unit> {
                secContinuation = it
                println("在第2个挂起点挂起")
            }

            println("协程干事3")

            suspendCoroutine<Unit> {
                thirdContinuation = it
                println("在第3个挂起点挂起")

            }
            println("协程干事4")

            suspendCoroutine<Unit> {
                fourthContinuation = it
                println("在第4个挂起点挂起")
            }

            println("协程干事5")

            Unit

        }.createCoroutine(object : Continuation<Unit> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Unit>) {
            }

        })

        continuation.resume(Unit)

        println("协程挂起了，我在主流程做点事1")

        firstContinuation?.resume(Unit)

        println("协程挂起了，我在主流程做点事2")

        secContinuation?.resume(Unit)

        println("协程挂起了，我在主流程做点事3")

        thirdContinuation?.resume(Unit)

        println("协程挂起了，我在主流程做点事4")

        fourthContinuation?.resume(Unit)

        println("协程挂起了，我在主流程做点事5")

        Thread.sleep(100000)
    }
}