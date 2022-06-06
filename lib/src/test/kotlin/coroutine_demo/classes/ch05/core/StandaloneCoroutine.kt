package coroutine_demo.classes.ch05.core

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class StandaloneCoroutine(context: CoroutineContext) : AbstractCoroutine<Unit>(context) {

    override fun handleJobException(e: Throwable): Boolean {
        super.handleJobException(e)
        context[CoroutineExceptionHandler]?.handleException(context, e) ?:
                Thread.currentThread().let { it.uncaughtExceptionHandler.uncaughtException(it, e) }
        return true
    }

}