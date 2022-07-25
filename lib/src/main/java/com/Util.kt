package com

import com.kotlin_demo.coroutines_demo.scope_demo.log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


suspend fun main() {
    val flow = flow<Int> {
        emit(1)
        delay(1000)
        emit(2)
        emit(3)
    }
    flow.flowOn(Dispatchers.IO).collect {
        log(it.toString())
    }
}

