package coroutine_demo.classes.ch05

import common.DispatcherContext
import common.SingleDispatcher
import common.log
import coroutine_demo.classes.ch05.scope.GlobalScope
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.*

/**
 * 拦截器是协程上下文的一种，
 * 可以将将原来的SuspendLambda做一层代理。
 *
 *  Continuation的层次
 *   ------------------------------------------
 *  |    SafeContinuation
 *  |   ----------------------------------
 *  |   |     Interceptor返回的Continuation
 *  |   |
 *  |   |     ---------------
 *  |   |    | SuspendLambda
 *  |   |    ----------------
|   |   |
 *  |   -----------------------------------
 *  -------------------------------------------
 */

class MySimpleInterceptor : ContinuationInterceptor {
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return object : Continuation<T> {
            override val context: CoroutineContext
                get() = continuation.context

            override fun resumeWith(result: Result<T>) {
                println("代理的内容1")
                continuation.resumeWith(result)
                println("代理的内容2")
            }

        }
    }
}

class 使用协程的拦截器实现线程调度 {
    @Test
    fun 简单使用拦截器切换线程() {
        GlobalScope.launch(MySimpleInterceptor()) {
            println("1")
        }
    }

    @Test
    fun 使用调度器切保证协程体在固定线程中运行() {
        Thread.currentThread().name = "main"
        GlobalScope.launch(DispatcherContext(SingleDispatcher)) {
            log("1")
            suspendCoroutine<Unit> {
                log("2")
                thread(name = "thread-0") {
                    Thread.sleep(3000)
                    log("3")
                    it.resume(Unit)
                }
            }
            log("4")
        }
        Thread.sleep(20000)
    }
}