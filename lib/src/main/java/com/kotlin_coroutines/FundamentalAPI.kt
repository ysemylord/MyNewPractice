package com.kotlin_coroutines

import kotlin.coroutines.*

fun main() {
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

