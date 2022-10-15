package ch04

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import log
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.*

/**
 * 使用suspend-coroutine函数将 异步回调 转化为 同步代码 的
 */

class SuspendCoroutineDemo {


    /**
     * 异步回调
     */
    fun fetchNameAsync(call: (name: String) -> Unit) {
        thread {
            Thread.sleep(2000)
            call("name")
        }
    }

    /**
     * 使用suspend-coroutine函数将 异步回调 转化为 同步代码 的
     */
    private suspend fun fetchNameSync(): String = suspendCoroutine<String> {
        thread {
            Thread.sleep(2000)//模拟网络请求耗时
            val jsonString = "{name:jack}"//模拟返回的数据
            it.resume(jsonString)
        }
    }


    /**
     * 使用异步回调
     */
    @Test
    fun testAsync() {
        fetchNameAsync { name ->
            name.log()
        }
        Thread.sleep(20000)
    }

    @Test
    fun tesSync0() {

        suspend {
            val name = fetchNameSync()
            name.log()
        }.startCoroutine(object : Continuation<Unit> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Unit>) {

            }
        })
        Thread.sleep(20000)
    }


    class TestScope {
        fun tagName(name: String) = "tag $name"
    }

    @Test
    fun tesSync1() {

        val suspend: suspend TestScope.() -> Unit = {
            val name = fetchNameSync()
            tagName(name)
        }

        suspend.createCoroutine(TestScope(), object : Continuation<Unit> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Unit>) {

            }
        }).resume(Unit)

        Thread.sleep(20000)
    }
}

