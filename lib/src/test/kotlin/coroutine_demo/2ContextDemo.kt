package com.kotlin_coroutines.context_demo

import org.junit.Test
import kotlin.coroutines.*
import kotlin.random.Random

class `2ContextDemo` {
    /**
     * title 协程上下文
     */
    class CoroutineName(val name: String) : AbstractCoroutineContextElement(Key) {
        companion object Key : CoroutineContext.Key<CoroutineName>
    }

    class CoroutineExceptionHandler(val onErrorAction: (Throwable) -> Unit) :
        AbstractCoroutineContextElement(Key) {
        companion object Key : CoroutineContext.Key<CoroutineExceptionHandler>

        fun onError(error: Throwable) {
            error.printStackTrace()
            onErrorAction(error)
        }
    }

    @Test
    fun testContext() {
        var myCoroutineContext: CoroutineContext = EmptyCoroutineContext
        myCoroutineContext += CoroutineName("CoroutineName")
        myCoroutineContext += CoroutineExceptionHandler { error ->
            error.printStackTrace()
        }
        suspend {
            println(coroutineContext.hashCode())
            println(myCoroutineContext[CoroutineName]?.name)
            1 / 1
        }.startCoroutine(object : Continuation<Int> {
            override val context = myCoroutineContext
            override fun resumeWith(result: Result<Int>) {
                println(context.hashCode())
                result.onFailure {
                    context[CoroutineExceptionHandler]?.onError(it)
                }
            }
        })
    }

    @Test
    fun testContext2() {
        //创建了一个协程体上下文
        var myCoroutineContext: CoroutineContext = EmptyCoroutineContext
        myCoroutineContext += CoroutineName("co-01")
        myCoroutineContext += CoroutineExceptionHandler {
            println(it)
        }
        suspend {
            //将自己的myCoroutineContext赋值给协程体的协程上下文
            coroutineContext + myCoroutineContext
            val res = random()
            if (res) {
                100
            } else {
                throw ArithmeticException()
            }
        }.startCoroutine(object : Continuation<Int> {

            override val context = myCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                result.onFailure {
                    context[CoroutineExceptionHandler]?.onError(it)
                }.onSuccess {
                    println("Result $it")
                }
            }
        })
    }

    private suspend fun random(): Boolean {
        println("In Coroutine [${coroutineContext[CoroutineName]?.name}].")
        val res = suspendCoroutine<Boolean> { continuation -> //挂起
            Thread.sleep(2000)//模拟耗时任务
            val res = Random.nextBoolean()//产生随机结果
            continuation.resumeWith(Result.success(res))//恢复
        }
        return res
    }
}