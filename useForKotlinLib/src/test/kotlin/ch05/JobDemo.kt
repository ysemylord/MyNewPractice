package ch05

import kotlinx.coroutines.*
import log
import org.junit.Test
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

class JobDemo {
    @Test
    fun one() {
        GlobalScope.launch {
            suspendCancellableCoroutine { continuation ->
                continuation.invokeOnCancellation {
                    "${coroutineContext[Job]?.isCancelled}".log()//false 在调用取消回调时，isCancelled为false，因为此时的状态是正在取消
                }
                continuation.cancel()
            }
        }
        Thread.sleep(1000000)
    }

    @Test
    fun two1() {
        val job = GlobalScope.launch {
            delay(100)

        }
        job.invokeOnCompletion {
            "onCompletion isCompleted ${job.isCompleted}".log()//执行完成回调时，isCompleted=true
        }
        Thread.sleep(10000)
    }

    @Test
    fun three() = runBlocking {
        val job = GlobalScope.launch(CoroutineExceptionHandler {
            //注意cancel时抛出的CancellationException，不会交给异常处理器
                coroutineContext, throwable ->
            "异常".log()
        }) {
            "1".log()

            //协程挂起
            delay(5000)

            "2".log()
        }


        "active: ${job.isActive}".log()
        delay(2000)
        "active ${job.isActive}".log()//协程挂起后，还是视作Active的


        job.invokeOnCompletion {
            "onCompletion isCompleted ${job.isCompleted}".log() //执行完成isCompleted true
            "onCompletion isActive ${job.isActive}".log()//完成时，Active就是false了
            "onCompletion isCancelled ${job.isCancelled}".log()
            it?.toString()?.log()//如果是因为cancel导致的完成回调，throwable不为空
        }


        // job.cancel()

        job.join()
    }

}

