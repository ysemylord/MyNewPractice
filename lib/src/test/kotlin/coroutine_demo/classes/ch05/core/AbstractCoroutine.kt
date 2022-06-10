package coroutine_demo.classes.ch05.core

import coroutine_demo.classes.ch05.CancellationException
import coroutine_demo.classes.ch05.Job
import coroutine_demo.classes.ch05.OnCancel
import coroutine_demo.classes.ch05.OnComplete
import coroutine_demo.classes.ch05.exception.CoroutineExceptionHandler
import coroutine_demo.classes.ch05.scope.CoroutineScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume

/**
 * AbstractCoroutine本质上是结束回调
 * 在协程框架层面，可以看做是协程本身
 */
abstract class AbstractCoroutine<T>(context: CoroutineContext) : Job, Continuation<T>,
    CoroutineScope {

    protected val state = AtomicReference<CoroutineState>()

    override val context: CoroutineContext

    override val scopeContext: CoroutineContext
        get() = context

    protected val parentJob = context[Job]//父协程

    private var parentCancelDisposable: Disposable? = null

    init {
        state.set(CoroutineState.InComplete())
        this.context = context + this//将Job添加进协程上下文中.(Job也是CoroutineContext.Element，key为Job)

        //给父协程注册一个父协程取消时的回调
        //父协程取消时，调用子协程的canel()
        parentCancelDisposable = parentJob?.invokeOnCancel {
            cancel()
        }
    }

    val isCompleted
        get() = state.get() is CoroutineState.Complete<*>

    override val isActive: Boolean
        get() = when (state.get()) {
            is CoroutineState.Complete<*>,
            is CoroutineState.Cancelling -> false
            else -> true
        }

    override fun resumeWith(result: Result<T>) {
        val newState = state.updateAndGet { prevState ->
            when (prevState) {
                //although cancelled, flows of job may work out with the normal result.
                is CoroutineState.Cancelling,
                is CoroutineState.InComplete -> {
                    /*if (prevState is CoroutineState.Cancelling) {
                        println("协程已经cancel啦 ${result.exceptionOrNull()}")
                    }*/
                    CoroutineState.Complete(result.getOrNull(), result.exceptionOrNull())
                        .from(prevState)
                }
                is CoroutineState.Complete<*> -> {
                    throw IllegalStateException("Already completed!")
                }
            }
        }

        (newState as CoroutineState.Complete<T>).exception?.let {
            tryHandleException(it)
        }

        newState.notifyCompletion(result)
        newState.clear()
        parentCancelDisposable?.dispose()
    }

    private fun tryHandleException(t: Throwable): Boolean =
        when (t) {
            is CancellationException -> false
            else -> handleJobException(t)
        }


    override suspend fun join() {
        when (state.get()) {
            is CoroutineState.InComplete,
            is CoroutineState.Cancelling -> return joinSuspend()
            is CoroutineState.Complete<*> -> {
                val currentCallingJobState = coroutineContext[Job]?.isActive ?: return
                if (!currentCallingJobState) {
                    throw CancellationException("Coroutine is cancelled.")
                }
                return
            }
        }
    }

    private suspend fun joinSuspend() = suspendCancellableCoroutine<Unit> { continuation ->
        val disposable = doOnCompleted { result ->
            continuation.resume(Unit)
        }
        continuation.invokeOnCancellation { disposable.dispose() }
    }

    /**
     * 添加取消时的回调和完成回调类似
     * 协程当前的状态是未完成，才添加取消回调
     * 如果是取消中则直接调用传入的回调
     */
    override fun invokeOnCancel(onCancel: OnCancel): Disposable {
        val disposable = CancellationHandlerDisposable(this, onCancel)
        val newState = state.updateAndGet { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prev).with(disposable)
                }
                is CoroutineState.Cancelling,
                is CoroutineState.Complete<*> -> {
                    prev
                }
            }
        }
        (newState as? CoroutineState.Cancelling)?.let {
            // call immediately when Cancelling.
            onCancel()
        }
        return disposable
    }


    /**
     * 取消协程
     * 如果当前状态是未完成，将状态改为取消中
     * 调用所有注册的取消回调
     */
    override fun cancel() {
        val prevState = state.getAndUpdate { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.Cancelling().from(prev)//如果当前状态是未完成，将状态改为取消中
                }
                is CoroutineState.Cancelling,
                is CoroutineState.Complete<*> -> prev
            }
        }

        if (prevState is CoroutineState.InComplete) {
            prevState.notifyCancellation()//调用所有注册的取消回调
        }
        parentCancelDisposable?.dispose()//取消父协程注册的回调
    }

    protected fun doOnCompleted(block: (Result<T>) -> Unit): Disposable {
        val disposable = CompletionHandlerDisposable(this, block)
        val newState = state.updateAndGet { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prev).with(disposable)
                }
                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(prev).with(disposable)
                }
                is CoroutineState.Complete<*> -> {
                    prev
                }
            }
        }
        (newState as? CoroutineState.Complete<T>)?.let {
            block(
                when {
                    it.exception != null -> Result.failure(it.exception)
                    it.value != null -> Result.success(it.value)
                    else -> throw IllegalStateException("Won't happen.")
                }
            )
        }
        return disposable
    }


    override fun invokeOnCompletion(onComplete: OnComplete): Disposable {
        return doOnCompleted { _ -> onComplete() }
    }

    override fun remove(disposable: Disposable) {
        state.updateAndGet { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prev).without(disposable)
                }
                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(prev).without(disposable)
                }
                is CoroutineState.Complete<*> -> {
                    prev
                }
            }
        }
    }


    protected open fun handleJobException(e: Throwable) = false

    override fun toString(): String {
        return context[CoroutineName].toString()
    }
}