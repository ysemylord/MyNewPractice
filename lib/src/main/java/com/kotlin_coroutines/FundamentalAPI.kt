package com.kotlin_coroutines

import kotlin.coroutines.*

/**
 * title startCoroutine  createCoroutine
 */
fun main() {
    startCoroutine()
}

private fun startCoroutine() {
    suspend {
        println("In Coroutine")
        4
    }.startCoroutine(
        object : Continuation<Int> {
            override val context = EmptyCoroutineContext
            override fun resumeWith(result: Result<Int>) {
                if(result.isSuccess){

                }else if(result.isFailure){

                }
            }
        }
    )
}

private fun createCoroutine() {
    val continuation = suspend {
        println("In Coroutine")
        5
    }.createCoroutine(
        object : Continuation<Int> {
            override val context = EmptyCoroutineContext
            override fun resumeWith(result: Result<Int>) {
                println("Coroutine End: $result")
            }
        }
    )
    continuation.resume(Unit)
}

