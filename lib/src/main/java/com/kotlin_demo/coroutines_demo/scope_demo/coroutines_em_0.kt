package com.kotlin_demo.coroutines_demo.scope_demo

import kotlinx.coroutines.*

fun main() {

    GlobalScope.launch {
        println("GlobalScope      : I'm working in thread ${Thread.currentThread().name}")
    }

    runBlocking<Unit> {

        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")

        launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
            println("Default               : I'm working in thread ${Thread.currentThread().name}")
        }

        launch { // context of the parent, main runBlocking coroutine
            println("extends from main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        }

    }


}
