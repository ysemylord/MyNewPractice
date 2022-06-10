package coroutine_demo.classes.ch05.exception

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * 定义协程处理的接口，
 * 并定义好Key和对应的接口
 */
interface CoroutineExceptionHandler : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<CoroutineExceptionHandler>

    fun handleException(context: CoroutineContext, e: Throwable)
}

/**
 * 创建CoroutineExceptionHandler的实例
 */
fun CoroutineExceptionHandler(handler: (context: CoroutineContext, e: Throwable) -> Unit): CoroutineExceptionHandler =
    object : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
        override fun handleException(context: CoroutineContext, e: Throwable) {
            handler(context, e)
        }
    }


/*
fun CoroutineExceptionHandler(handler: (context: CoroutineContext, e: Throwable) -> Unit): CoroutineExceptionHandler {
    return object : CoroutineExceptionHandler {
        override fun handleException(context: CoroutineContext, e: Throwable) {
            handler(context, e)
        }

        override val key = CoroutineExceptionHandler.Key

    }
}*/
