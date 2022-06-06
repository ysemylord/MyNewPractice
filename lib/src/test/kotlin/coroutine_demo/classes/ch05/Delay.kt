package coroutine_demo.classes.ch05

import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 简单实现
 */
/*suspend fun delay(time: Long) {
    return suspendCoroutine { continuation ->
        thread {
            Thread.sleep(time)
            continuation.resume(Unit)
        }
    }
}*/

/**
 * 稍微优化下
 */
private val executor = Executors.newScheduledThreadPool(1, object : ThreadFactory {
    override fun newThread(runnable: Runnable): Thread {
        return Thread(runnable, "delay schedule thread").apply { isDaemon = true }
    }
})

suspend fun delay(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS) =
    suspendCoroutine<Unit> { continuation ->
        executor.schedule({
            continuation.resume(Unit)
        }, time, unit)
    }
