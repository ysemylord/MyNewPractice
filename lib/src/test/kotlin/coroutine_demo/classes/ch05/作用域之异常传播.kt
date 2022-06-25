package coroutine_demo.classes.ch05

import com.kotlin_demo.coroutines_demo.scope_demo.log
import kotlinx.coroutines.*

import org.junit.Test

/**
 * 因为这部分自己写的框架不完善，所以使用官方的框架
 */
class 作用域之异常传播 {
    /**
     * 输出结果
     * [DefaultDispatcher-worker-0] 1
     * [DefaultDispatcher-worker-0] 4
     * [DefaultDispatcher-worker-1] 2
     * 没有输出3，是因为parentJob取消了，childJob也取消了
     *
     * 代码内部的实现方式
     *   parentCancelDisposable = parentJob?.invokeOnCancel {
    cancel()
    }
     *
     */
    @Test
    fun 父协程取消子协程也取消() {
        val parentJob = GlobalScope.launch {
            log("1")
            launch {
                log("2")
                delay(5000)
                log("3")
            }
            log("4")
        }
        Thread.sleep(2000)
        parentJob.cancel()
        Thread.sleep(30000)
    }

    @Test
    fun 父协程发生异常子协程也取消() {
        GlobalScope.launch(CoroutineExceptionHandler { coroutineContext, throwable -> log("exception") }) {
            log("1")
            launch {
                log("2")
                delay(5000)
                log("3")
            }
            log("4")
            delay(2000)
            val i = 4 / 0
            log("5")
        }
        Thread.sleep(2000)
        Thread.sleep(30000)
    }

    @Test
    fun 子协程取消时父协程不受影响() {
        GlobalScope.launch(CoroutineExceptionHandler { coroutineContext, throwable -> log("exception") }) {
            log("1")
            val childJob = launch {
                log("2")
                log("3")
            }
            childJob.cancel()
            log("4")
            delay(2000)
            log("5")
        }
        Thread.sleep(2000)
        Thread.sleep(30000)
    }

    @Test
    fun `子协程所在作用域为协同作用域,子协程发生异常,异常向上抛出1`() {
        GlobalScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            log("parent CoroutineExceptionHandler exception ")
        }) {
            log("1")
                launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                    log("child CoroutineExceptionHandler exception ")
                }) {
                    log("2")
                    val i = 1 / 0
                    log("3")
                }
            delay(2000)
            log("4")
        }
        Thread.sleep(30000)
    }

    @Test
    fun `子协程所在作用域为协同作用域,子协程发生异常,异常向上抛出2`() {
        GlobalScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            log("parent CoroutineExceptionHandler exception ")
        }) {
            log("1")
            coroutineScope {
                launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                    log("child CoroutineExceptionHandler exception ")
                }) {
                    log("2")
                    val i = 1 / 0
                    log("3")
                }
            }
            delay(2000)
            log("4")
        }
        Thread.sleep(30000)
    }

    @Test
    fun `子协程所在作用域为主从作用域,子协程发生异常,异常自己处理`() {
        GlobalScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            log("parent CoroutineExceptionHandler exception ")
        }) {
            log("1")
            supervisorScope {
                launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                    log("child CoroutineExceptionHandler exception ")
                }) {
                    log("2")
                    val i = 1 / 0
                    log("3")
                }
            }
            delay(2000)
            log("4")
        }
        Thread.sleep(30000)
    }

}