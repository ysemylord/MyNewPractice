package com.kotlin_demo.coroutines_demo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
    runBlocking {
          val avator = getAvator()
          print(avator)
    }
}

private suspend fun getAvator(): String {
    return withContext(Dispatchers.IO) {
        delay(1000)
        "avator"
    }
}