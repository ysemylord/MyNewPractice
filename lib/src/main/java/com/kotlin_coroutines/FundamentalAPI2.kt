package com.kotlin_coroutines

import kotlin.coroutines.*

/**
 * title 带有接受者的startCoroutine
 */
fun main() {
    startCoroutine2()
}

private fun startCoroutine1() {
    //带有接收者的函数
    val receiverSuspendLambda: suspend ProducerScope<Int>.() -> Int = { produce(1) }

     receiverSuspendLambda.startCoroutine(ProducerScope(), object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println("$result")
        }
    })

}


private fun startCoroutine2() {
    lanch(ProducerScope<Int>()) {
        produce(1)
    }
}

fun <R, T> lanch(recevier: R, block: suspend R.() -> T) {
    block.startCoroutine(recevier, object : Continuation<T> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<T>) {
            println("$result")
        }

    })
}

class ProducerScope<T> {
    suspend fun produce(value: T):T {
        return value
    }
}

