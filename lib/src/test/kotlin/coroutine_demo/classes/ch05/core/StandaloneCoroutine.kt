package coroutine_demo.classes.ch05.core


import coroutine_demo.classes.ch05.exception.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class StandaloneCoroutine(context: CoroutineContext) : AbstractCoroutine<Unit>(context) {

    override fun handleJobException(e: Throwable): Boolean {
        context[CoroutineExceptionHandler]?.handleException(context, e) ?: run {
            Thread.currentThread().run { uncaughtExceptionHandler.uncaughtException(this, e) }
        }
        return true
    }
}