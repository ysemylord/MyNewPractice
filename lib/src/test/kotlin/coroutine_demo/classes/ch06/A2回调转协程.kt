package coroutine_demo.classes.ch06

import com.kotlin_demo.coroutines_demo.scope_demo.log
import common.User
import common.githubApi
import kotlinx.coroutines.*

import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class A2回调转协程 {

    /**
     * Call 如果使用回调方式，那Call API的设计方式如下
     */

    @Test
    fun 回调方式() {
        val call = githubApi.getUserCallback("bennyhuo")
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.takeIf { it.isSuccessful }?.run { log(response.body().toString()) }
                    ?: run { log("请求失败") }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                log(t.message!!)
            }

        })
    }

    /**
     * Call 转为协程的写法
     */
    suspend fun <T> Call<T>.await(): T {
        return suspendCancellableCoroutine<T> { continuation ->
            continuation.invokeOnCancellation {
                cancel()
                log("invokeOnCancellation")
            }
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    log("onResponse")
                    response.takeIf { it.isSuccessful }?.body()?.run {
                        continuation.resume(this)
                    } ?: run {
                        continuation.resumeWithException(HttpException(response))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    log("onFailure")
                    continuation.resumeWithException(t)
                }
            })
        }
    }


    @Test
    fun Call协程写法() {
        /**
         * 注意：我们这里是手动取消，所以异常处理器不会收到这个异常
         */
        GlobalScope.launch(CoroutineExceptionHandler { _, t -> log(t.toString()) }) {
            val call = githubApi.getUserCallback("bennyhuo")
            val res = call.await()
            log(res.toString())
        }.cancel()
        Thread.sleep(20000)
    }

    /**
     *CompletableFuture
     */
    @Test
    fun useCompletableFuture() {
        CompletableFuture.supplyAsync {
            3
        }.thenAccept {
            log(it.toString())
        }
    }

    /**
     * 改为协程的方式
     */
    suspend fun <T> CompletableFuture<T>.await(): T {
        if (isDone) {
            try {
                return get()
            } catch (e: Exception) {
                throw e
            }
        }
        return suspendCancellableCoroutine<T> { cancellableContinuation ->
            cancellableContinuation.invokeOnCancellation {
                this.cancel(true)
            }
            whenComplete { value, throwable ->
                throwable?.run {
                    cancellableContinuation.resumeWithException(throwable)
                } ?: run {
                    cancellableContinuation.resume(value)
                }
            }
        }
    }

    @Test
    fun useCompletableFutureByCoroutine() {
        GlobalScope.launch {
            val completableFuture = CompletableFuture.supplyAsync {
                Thread.sleep(3000)
                3
            }
            val res = completableFuture.await()
            log(res.toString())
        }

        Thread.sleep(5000)

    }

}