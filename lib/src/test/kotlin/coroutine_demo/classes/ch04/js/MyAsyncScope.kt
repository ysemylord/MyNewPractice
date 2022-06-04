package coroutine_demo.classes.ch04.js

import android.os.Handler
import android.os.Looper
import common.Dispatcher
import common.DispatcherContext
import common.githubApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.*


interface MyAwaitScope

/**
 * 开启一个协程
 */
fun myAsync(dispatcher: CoroutineContext, block: suspend MyAwaitScope.() -> Unit) {
    //这里添加一个MyAwaitScope,用于限定myAwait的只能在myAsync内部调用
    block.startCoroutine(object : MyAwaitScope {}, object : Continuation<Unit> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext + dispatcher

        override fun resumeWith(result: Result<Unit>) {
            result.getOrThrow()
            println("协程结束")
        }

    })
}

/**
 * 挂起协程，等待返回
 * T:返回值类型
 */
suspend fun <T> MyAwaitScope.myAwait(block: () -> Call<T>): T {
    return suspendCoroutine<T> { continuation ->
        val call = block()
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                println("响应所在的线程 ${Thread.currentThread()}")
                continuation.resume(response.body()!!)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }

        })
    }
}

fun main() {
    Looper.prepare()
    val handlerDispatcher =
        DispatcherContext(object :
            Dispatcher {
            val handler = Handler()
            override fun dispatch(block: () -> Unit) {
                handler.post(block)
            }
        })
    myAsync(handlerDispatcher) {
        println("协程开启所在的线程 ${Thread.currentThread()}")
        val user = myAwait { githubApi.getUserCallback("bennyhuo") }
        println("获取结果所在的线程 ${Thread.currentThread()}")
        println(user)
    }

    Looper.loop()
}



