package coroutine_demo.classes.ch05.core

import coroutine_demo.classes.ch05.Job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 定义接口
 */
interface Deffered<T>: Job {
    /**
     * 等待任务
     */
    suspend fun await(): T
}

class DeferredCoroutine<T>(context: CoroutineContext) : AbstractCoroutine<T>(context), Deffered<T> {
    override suspend fun await(): T {
        val currentState = state.get()
        return when (currentState) {
            //如果协程没有执行完毕，挂起等待结果
            is CoroutineState.InComplete,
            is CoroutineState.Cancelling -> suspendAwait()
            //如果协程执行完毕，直接返回结果
            is CoroutineState.Complete<*> -> {
                return (currentState.value as T?) ?: throw  currentState.exception!!
            }
        }
    }

    private suspend fun suspendAwait(): T {
        return suspendCoroutine<T> { continuation ->
            doOnCompleted {
                continuation.resumeWith(it)
            }
        }
    }
}