package coroutine_demo.classes.ch04.lua

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*

/**
 * 仿Lua的协程
 */
sealed class Status {
    class Created(val continuation: Continuation<Unit>): Status()
    class Yielded<P>(val continuation: Continuation<P>): Status()
    class Resumed<R>(val continuation: Continuation<R>): Status()
    object Dead: Status()
}

interface CoroutineScope<P, R> {
    val parameter: P?

    suspend fun yield(value: R): P
}

class Coroutine< P, R> (
    override val context: CoroutineContext = EmptyCoroutineContext,
    private val block: suspend CoroutineScope<P, R>.(P) -> R
): Continuation<R> {

    companion object {
        fun <P, R> create(
            context: CoroutineContext = EmptyCoroutineContext,
            block: suspend CoroutineScope<P, R>.(P) -> R
        ): Coroutine<P, R> {
            return Coroutine(context, block)
        }
    }

    private val scope = object : CoroutineScope<P, R> {
        override var parameter: P? = null

        /**
         * 语义其实是 比如:A.yield() 让协程A挂起，让协程A所在的协程恢复
         */
        override suspend fun yield(value: R): P = suspendCoroutine { continuation ->
            val previousStatus = status.getAndUpdate {
                when(it) {
                    is Status.Created -> throw IllegalStateException("Never started!")
                    is Status.Yielded<*> -> throw IllegalStateException("Already yielded!")
                    is Status.Resumed<*> -> Status.Yielded(continuation)
                    Status.Dead -> throw IllegalStateException("Already dead!")
                }
            }

            (previousStatus as? Status.Resumed<R>)?.continuation?.resume(value)
        }
    }

    private val status: AtomicReference<Status>

    val isActive: Boolean
        get() = status.get() != Status.Dead

    init {
        val coroutineBlock: suspend CoroutineScope<P, R>.() -> R = { block(parameter!!) }
        val start = coroutineBlock.createCoroutine(scope, this)
        status = AtomicReference(Status.Created(start))
    }

    override fun resumeWith(result: Result<R>) {
        val previousStatus = status.getAndUpdate {
            when(it) {
                is Status.Created -> throw IllegalStateException("Never started!")
                is Status.Yielded<*> -> throw IllegalStateException("Already yielded!")
                is Status.Resumed<*> -> {
                    Status.Dead
                }
                Status.Dead -> throw IllegalStateException("Already dead!")
            }
        }
        (previousStatus as? Status.Resumed<R>)?.continuation?.resumeWith(result)

    }

    /**
     * 这个resume不是Continuation.resume
     * 它是一个挂起函数
     * 语义其实是 A.resume() 让协程A恢复执行，让A.resume()这行代码所在的协程挂起
     */
    suspend fun resume(value: P): R = suspendCoroutine { continuation ->
        val previousStatus = status.getAndUpdate {
            when(it) {
                is Status.Created -> {
                    scope.parameter = value
                    Status.Resumed(continuation)
                }
                is Status.Yielded<*> -> {
                    Status.Resumed(continuation)
                }
                is Status.Resumed<*> -> throw IllegalStateException("Already resumed!")
                Status.Dead -> throw IllegalStateException("Already dead!")
            }
        }

        when(previousStatus){
            is Status.Created -> previousStatus.continuation.resume(Unit)
            is Status.Yielded<*> -> (previousStatus as Status.Yielded<P>).continuation.resume(value)
            else -> {}
        }
    }

    suspend fun <SymT> SymCoroutine<SymT>.yield(value: R): P {
        return scope.yield(value)
    }
}

open class Dispatcher: ContinuationInterceptor {
    override val key = ContinuationInterceptor

    private val executor = Executors.newSingleThreadExecutor()

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return DispatcherContinuation(continuation, executor)
    }
}

class DispatcherContinuation<T>(val continuation: Continuation<T>, val executor: Executor): Continuation<T> by continuation {

    override fun resumeWith(result: Result<T>) {
        executor.execute {
            continuation.resumeWith(result)
        }
    }
}

suspend fun main() {
    val producer = Coroutine.create<Unit, Int>(Dispatcher()) {
        for (i in 0..3) {
            println("send $i")
            yield(i)
        }
        200
    }

    val consumer = Coroutine.create<Int, Unit>(Dispatcher()) { param: Int ->
        println("start $param")
        for (i in 0..3) {
            val value = yield(Unit)
            println("receive $value")
        }
    }

    while (producer.isActive && consumer.isActive){
        val result = producer.resume(Unit)
        consumer.resume(result)
    }
}