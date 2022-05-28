package coroutine_demo

import com.kotlin_coroutines.suspendFunc02
import org.junit.Test
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine

/**
 * title 协程拦截器
 * 拦截器也是一个协程体元素
 */
class `3IntercepterDemo` {
    class LogInterceptor : ContinuationInterceptor {
        override val key = ContinuationInterceptor
        override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
            //将原本的协程体转化为了LogContinuation
            return LogContinuation(continuation)
        }
    }

    @Test
    fun test(){
        suspend {
            //suspendFunc02内部会调用continuation.resume
            suspendFunc02()
            suspendFunc02()
        }.startCoroutine(object : Continuation<Int> {//startCoroutine会调用resume开启协程
            override val context = LogInterceptor()
            override fun resumeWith(result: Result<Int>) {
                result.getOrThrow()
                println(context)
            }
        })
    }
}

class LogContinuation<T>(private val continuation: Continuation<T>) :
    Continuation<T> by continuation {
    override fun resumeWith(result: Result<T>) {
        println("before resumeWith: $result")
        continuation.resumeWith(result)
        println("after resumeWith.")
        println(continuation.context)
    }
}
