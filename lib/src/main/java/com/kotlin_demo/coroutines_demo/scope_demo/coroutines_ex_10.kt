package com.kotlin_demo.coroutines_demo.scope_demo

import kotlinx.coroutines.*


class Activity {
    private val mainScope = CoroutineScope(Dispatchers.Default)

    fun destroy() {
        mainScope.cancel()
    }

    fun doSomeThing() {
        repeat(10) { i ->
            mainScope.launch {
                delay((i+1)*200L)
                println("Coroutine $i is done")
            }
        }
    }
}

fun main() {
    runBlocking {
        val activity = Activity()
        activity.doSomeThing()
        delay(500)
        activity.destroy()
    }
}