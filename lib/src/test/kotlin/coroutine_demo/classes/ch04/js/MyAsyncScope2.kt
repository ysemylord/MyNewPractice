package coroutine_demo.classes.ch04.js

import android.os.Handler
import android.os.Looper
import common.Dispatcher
import common.DispatcherContext
import common.User
import common.githubApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.*


interface MyAwaitScope2

/**
 * 开启一个协程
 */
fun myAsync2(dispatcher: CoroutineContext, block: suspend MyAwaitScope.() -> Unit) {
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
 *
 * myAwait2 没对挂起函数的操作做任何限制，全部由外部传入,更加灵活
 * myAwait  限制了外部必须传入一个Call,外部写的代码更少
 *
 */
suspend fun <T> MyAwaitScope.myAwait2(block: (continuation: Continuation<T>) -> Unit): T {
    return suspendCoroutine<T> { continuation ->
        block(continuation)
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
        val user = myAwait2<User> { continuation ->
            val userCall = githubApi.getUserCallback("bennyhuo")
            userCall.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    continuation.resume(response.body()!!)
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
        println("获取结果所在的线程 ${Thread.currentThread()}")
        println(user)
    }

    Looper.loop()
}

class MyAsyncScope {

}

