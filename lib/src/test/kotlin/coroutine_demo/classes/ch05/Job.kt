package coroutine_demo.classes.ch05

import coroutine_demo.classes.ch05.core.Disposable
import kotlin.coroutines.CoroutineContext

typealias OnComplete = () -> Unit

typealias CancellationException = java.util.concurrent.CancellationException
typealias OnCancel = () -> Unit

interface Job : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<Job>

    override val key: CoroutineContext.Key<*> get() = Job

    val isActive: Boolean

    /**
     * 设置协程取消后的回调
     */
    fun invokeOnCancel(onCancel: OnCancel): Disposable

    fun invokeOnCompletion(onComplete: OnComplete): Disposable

    /**
     * 取消协程
     */
    fun cancel()

    fun remove(disposable: Disposable)

    suspend fun join()
}