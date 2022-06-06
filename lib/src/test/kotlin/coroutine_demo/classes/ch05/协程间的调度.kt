package coroutine_demo.classes.ch05

import common.log
import coroutine_demo.classes.ch05.scope.CoroutineScope
import coroutine_demo.classes.ch05.scope.GlobalScope
import kotlinx.coroutines.CoroutineName
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Test2 {
}

/**
 *
 * 测试自定义的协程框架
 */
suspend fun main() {
    log("开启了一个主协程")
    val job = GlobalScope.launch {
        log("开启了一个子协程")
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
        delay(1000)
        log("5")
        log("子协程end")
    }
    log("回到了主协程")
    log("5")
    job.isActive
    job.join()
    log("主协程end")
}

