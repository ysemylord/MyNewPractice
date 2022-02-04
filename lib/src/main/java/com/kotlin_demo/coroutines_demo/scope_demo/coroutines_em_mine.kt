package com.kotlin_demo.coroutines_demo.scope_demo

import kotlinx.coroutines.*


fun main() {
    //协程都在一个作用域下执行

    //启动全局作用域的协程
    GlobalScope.launch {
        delay(1000)
        println("GlobalScope")
    }

    //在当前线程的基础上启动协程，会使当前线程阻塞
    //一般用在将当前线程转为为协程
    /**
     * fun main()=runBlocking{
     *
     * } 
     */

    runBlocking {
        delay(2000)
        println("runBlocking")
    }

    //自定义一个协程作用域
    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {
        delay(3000)
        println("custom scope")
    }
}

class coroutines_em_mine {
}