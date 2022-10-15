package ch05

import kotlinx.coroutines.*
import log
import org.junit.Test

class ScopeDemo {
    @Test
    fun one() {
        GlobalScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            "ddd".log()
        }) {
            launch {
                1 / 0
            }
        }

        Thread.sleep(20000000)
    }

    /**
     * 父协程取消，子协程取消
     * 注意：我们所谓的取消，都是在挂起点的取消
     */
    @Test
    fun two() {
        val parentJob =
            GlobalScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                "ddd".log()
            }) {
                val job = launch {
                    delay(2000)//delay这里是个挂起点，没了挂起点协程是取消不了的
                }
                job.invokeOnCompletion {
                    if (it is CancellationException) {
                        "子协程取消".log()
                    }
                }
            }

        parentJob.cancel()

        Thread.sleep(20000000)
    }

    /**
     * 子协程的取消不会导致父协程取消
     */
    @Test
    fun three() {
        val parentJob =
            GlobalScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                "ddd".log()
            }) {
                val job = launch {
                    delay(2000)
                    cancel()
                }

            }

        parentJob.invokeOnCompletion {
            if (it is CancellationException) {
                "父协程协程取消".log()
            }else{
                "父协程完成".log()
            }
        }

        Thread.sleep(20000000)
    }
}