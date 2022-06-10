package com

import com.kotlin_demo.coroutines_demo.scope_demo.log
import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.reflect.KClass

fun main() {
   GlobalScope.launch {
       throw Exception()
       log("1")
   }
    Thread.sleep(300000)

}