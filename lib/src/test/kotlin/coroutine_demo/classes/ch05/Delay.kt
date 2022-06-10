package coroutine_demo.classes.ch05

import coroutine_demo.classes.ch05.cancel.suspendCancellableCoroutine
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

/**
 * 支持取消
 */
suspend fun delay2(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS) =
    suspendCancellableCoroutine<Unit> { continuation ->
        val future= executor.schedule({
            continuation.resume(Unit)
        }, time, unit)
        //协程取消的时候，也把future这个任务取消啦
        continuation.invokeOnCancellation {
            future.cancel(true)
        }
    }