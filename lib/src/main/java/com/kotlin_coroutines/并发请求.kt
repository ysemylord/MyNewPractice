package com.kotlin_coroutines

import com.kotlin_demo.coroutines_demo.scope_demo.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class 并发请求 {
}

suspend fun main() {
    GlobalScope.launch {

        val res1 = async {
              Thread.sleep(2000)
              2
        }

        val res2 = async {

        }

        val res3 = async {
            Thread.sleep(4000)
            2
        }

        val r1 = res1.await()
        val r2 = res2.await()
        val r3 = res3.await()

        log("$r1 $r2 $r3")

    }

    Thread.sleep(50000)

}