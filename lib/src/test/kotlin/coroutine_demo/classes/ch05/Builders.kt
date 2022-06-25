package coroutine_demo.classes.ch05


import common.DispatcherContext
import common.Dispatchers
import coroutine_demo.classes.ch05.core.*
import coroutine_demo.classes.ch05.scope.CoroutineScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Deferred
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

private var coroutineIndex = AtomicInteger(0)

fun CoroutineScope.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
): Job {
    val completion = StandaloneCoroutine(newCoroutineContext(context))
    block.startCoroutine(completion, completion)
    return completion
}

fun <T> myRunBlocking(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): T {
    val eventQueue = BlockingQueueDispatcher()
    //ignore interceptor passed from outside.
    val newContext = context + DispatcherContext(eventQueue)
    val completion = BlockingCoroutine<T>(newContext, eventQueue)
    block.startCoroutine(completion, completion)
    return completion.joinBlocking()
}

fun <T> CoroutineScope.async(context: CoroutineContext = Dispatchers.Default, block: suspend CoroutineScope.() -> T): Deffered<T> {
    val completion = DeferredCoroutine<T>(newCoroutineContext(context))
    block.startCoroutine(completion, completion)
    return completion
}



fun CoroutineScope.newCoroutineContext(context: CoroutineContext): CoroutineContext {
    val combined =
        scopeContext + context + CoroutineName("@coroutine#${coroutineIndex.getAndIncrement()}")
    //如果协程上下文不是 Dispatchers.Default这个拦截拦截器，并且不包含拦截器
    //就添加一个Dispatchers.Default这个拦截器
    return if (combined !== Dispatchers.Default && combined[ContinuationInterceptor] == null) combined + Dispatchers.Default else combined
    //return combined
}