package ch05

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import log
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.random.Random

class CancelDemo {
    @Test
    fun one() {
       val job = GlobalScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
           "exception".log()
       }) {
            1/0
            suspendCancellableCoroutine { cancellableContinuation ->
                thread {
                    Thread.sleep(1000)
                    if (true) {
                        cancellableContinuation.cancel()
                    } else {
                        cancellableContinuation.resume(Unit)
                    }
                }
            }
        }
        job.cancel()
        Thread.sleep(2000000000000)
    }

    /**
     * 没有异常处理器，协程抛出了未处理的异常就会崩溃
     */
    @Test
    fun two() {
        val job = GlobalScope.launch() {
            1/0
        }
        Thread.sleep(2000000000000)
    }
}

