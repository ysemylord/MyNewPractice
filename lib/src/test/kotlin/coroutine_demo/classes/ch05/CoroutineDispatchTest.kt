package coroutine_demo.classes.ch05

import common.log
import coroutine_demo.classes.ch05.scope.GlobalScope
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.*

/**
 * 协程的调度探讨
 */
class CoroutineDispatchTest {
    @Test
    fun test1() {
        log("")
        GlobalScope.launch {
            log("主协程 1")
            GlobalScope.launch {
                log("子协程 1")
                suspendCoroutine<Unit> { continuation ->
                    log(" 2")
                    log(" 3")
                    // continuation.resume(Unit)
                    //这里如果不恢复子协程，会继续执行主协程的代码
                }
                log("子协程 4")
            }
        }
        log("主协程 2")
        log("主协程 3")
    }

    /**
     * 协程的调度的线程问题
     */
    @Test
    fun testThread() {
        Thread.currentThread().name = "main"
        GlobalScope.launch {
            log("main coroutine start")
            GlobalScope.launch {
                log("子协程 1")
                suspendCoroutine<Unit> { continuation ->
                    log(" 2")
                    thread(name = "thread 1") {
                        log("3")
                       // continuation.resume(Unit)
                    }
                }
                log("子协程 4")
            }
        }
        log("主协程 2")
        log("主协程 3")
    }
}

