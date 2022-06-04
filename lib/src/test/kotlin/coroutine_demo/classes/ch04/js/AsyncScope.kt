package coroutine_demo.classes.ch04.js

import android.os.Handler
import android.os.Looper
import common.Dispatcher
import common.DispatcherContext
import common.githubApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.concurrent.thread
import kotlin.coroutines.*

interface AsyncScope

suspend fun <T> AsyncScope.await(block: () -> Call<T>) = suspendCoroutine<T> {
    continuation ->
    val call = block()
    call.enqueue(object : Callback<T>{
        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWithException(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if(response.isSuccessful){
                response.body()?.let(continuation::resume) ?: continuation.resumeWithException(NullPointerException())
            } else {
                continuation.resumeWithException(HttpException(response))
            }
        }
    })
}

fun async(context: CoroutineContext = EmptyCoroutineContext, block: suspend AsyncScope.() -> Unit) {
    val completion = AsyncCoroutine(context)
    block.startCoroutine(completion, completion)
}

class AsyncCoroutine(override val context: CoroutineContext = EmptyCoroutineContext): Continuation<Unit>, AsyncScope {
    override fun resumeWith(result: Result<Unit>) {
        result.getOrThrow()
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

    thread {
        async(handlerDispatcher) {
            println("${Thread.currentThread()}")
            val user = await { githubApi.getUserCallback("bennyhuo") }
            println(user)
        }
    }


    Looper.loop()
}