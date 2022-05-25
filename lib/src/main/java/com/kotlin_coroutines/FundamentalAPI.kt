package com.kotlin_coroutines

import kotlin.coroutines.*

fun main() {

}

private fun startCoroutine() {
    suspend {
        println("In Coroutine")
        4
    }.startCoroutine(
        object : Continuation<Int> {
            override val context = EmptyCoroutineContext
            override fun resumeWith(result: Result<Int>) {
                println("Coroutine End: $result")
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

