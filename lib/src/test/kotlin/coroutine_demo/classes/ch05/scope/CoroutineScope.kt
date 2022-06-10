package coroutine_demo.classes.ch05.scope

import coroutine_demo.classes.ch05.Job
import coroutine_demo.classes.ch05.core.AbstractCoroutine
import kotlin.coroutines.*

/**
 * 协程作用域接口
 * 协程作用域持有当前协程的协程上下文
 */
interface CoroutineScope {
    val scopeContext: CoroutineContext
}

internal class ContextScope(context: CoroutineContext) : CoroutineScope {
    override val scopeContext: CoroutineContext = context
}


suspend fun <R> coroutineScope(block: suspend CoroutineScope.() -> R): R =
        suspendCoroutine { continuation ->
            val coroutine = ScopeCoroutine(continuation.context, continuation)
            block.startCoroutine(coroutine, coroutine)
        }
/**
 * 协同作用域
 */
internal open class ScopeCoroutine<T>(
        context: CoroutineContext,
        protected val continuation: Continuation<T>
) : AbstractCoroutine<T>(context),CoroutineScope {

    override fun resumeWith(result: Result<T>) {
        super.resumeWith(result)
        continuation.resumeWith(result)
    }
}

operator fun CoroutineScope.plus(context: CoroutineContext): CoroutineScope =
    ContextScope(scopeContext + context)

fun CoroutineScope.cancel() {
    val job = scopeContext[Job]
        ?: error("Scope cannot be cancelled because it does not have a job: $this")
    job.cancel()
}
