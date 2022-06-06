package coroutine_demo.classes.ch05.core

import common.Dispatcher
import common.log
import java.util.concurrent.LinkedBlockingDeque
import kotlin.coroutines.CoroutineContext

typealias EventTask = () -> Unit

class BlockingQueueDispatcher : LinkedBlockingDeque<EventTask>(), Dispatcher {
    override fun dispatch(block: EventTask) {
        log("put")
        offer(block)
    }
}

class BlockingCoroutine<T>(context: CoroutineContext, private val eventQueue: LinkedBlockingDeque<EventTask>) : AbstractCoroutine<T>(context) {

    /**
     * 循环从队列中拿出事件处理
     */
    fun joinBlocking(): T {
        while (!isCompleted) {
            log("take event")
            eventQueue.take().invoke()
        }
        return (state.get() as CoroutineState.Complete<T>).let {
            it.value ?: throw it.exception!!
        }
    }
}