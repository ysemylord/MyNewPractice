package coroutine_demo.classes.ch06

import com.kotlin_demo.coroutines_demo.scope_demo.log
import kotlinx.coroutines.*

import org.junit.Test

class A1启动模式 {
    /**
     * 默认调度器，立即调度，如果在调度前被取消，则进入取消响应的状态
     */
    @Test
    fun defaultMode() {
        Thread.currentThread().name = "main"
        log("主线程名")
        val job = GlobalScope.launch {
                log("1")
        }
        job.cancel()
        Thread.sleep(3000)
    }

    /**
     * atomic 调度器， 立即调度，第一个挂起点前不响应调度
     */
    @Test
    fun atomicMode() {
        Thread.currentThread().name = "main"
        log("主线程名")
        val job = GlobalScope.launch(start = CoroutineStart.ATOMIC) {
            log("1")
            delay(1000)
            log("2")
        }
        job.cancel()
        Thread.sleep(3000)
    }

    /**
     * lazy调度器， 只创建不调度，在需要时(start/join/wait方法调用时)调度
     */
    @Test
    fun lazyMode() {
        Thread.currentThread().name = "main"
        log("主线程名")
        val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
            log("1")
            delay(1000)
        }
        log("0")
        job.start()
        Thread.sleep(3000)
    }

    /**
     * unDispatched调度器，立即调度，但是在第一个挂起点前，协程运行在创建此协程的线程。
     */
    @Test
    fun unDispatchedMode() {
        Thread.currentThread().name = "main"
        log("主线程名 ")
        val job = GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
            log("0")
            delay(1000)
            log("1")
        }
        log("2")
        job.start()
        Thread.sleep(3000)
    }
}