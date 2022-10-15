package coroutine_demo.classes.ch04.python

import kotlin.concurrent.thread
import kotlin.coroutines.*

interface Generator<T> {
    operator fun iterator(): Iterator<T>
}

class GeneratorImpl<T>(
    private val block: suspend GeneratorScope<T>.(T) -> Unit,
    private val parameter: T
) :
    Generator<T> {
    override fun iterator(): Iterator<T> {
        return GeneratorIterator(block, parameter)
    }
}

sealed class State {
    class NotReady(val continuation: Continuation<Unit>) : State()
    class Ready<T>(val continuation: Continuation<Unit>, val nextValue: T) :
        State()

    object Done : State()
}

/**
 *
 * yield() 调用 suspendCoroutine 将当前协程挂起，执行suspendCoroutine{}中的代码
 * next()  调用Continuation.resume 恢复协程。
 *
 */
class GeneratorIterator<T>(
    private val block: suspend GeneratorScope<T>.(T) -> Unit,
    private val parameter: T
) : GeneratorScope<T>, Iterator<T>, Continuation<Any?> {
    override val context: CoroutineContext = EmptyCoroutineContext

    private var state: State

    init {
        val coroutineBlock: suspend GeneratorScope<T>.() -> Unit =
            { block(parameter) }
        val start = coroutineBlock.createCoroutine(this, this)
        state = State.NotReady(start)
    }

    /**
     * 这里返回了中断标志，因为这里就没有调用continuation.resume给continuation.value赋值
     * 所以continuation.getOrThrow返回的就是COROUTINE_SUSPENDED
     */
    override suspend fun yield(value: T):Unit {
        println("yield")
        return suspendCoroutine<Unit> { continuation ->
            state = when (state) {
                is State.NotReady -> State.Ready(continuation, value)
                is State.Ready<*> -> throw IllegalStateException("Cannot yield a value while ready.")
                State.Done -> throw IllegalStateException("Cannot yield a value while done.")
            }
        }
    }

    /**
     * 执行协程体，就是传进来的那个block,就是那个循环产生数据的过程
     */
    private fun resume() {
        when (val currentState = state) {
            is State.NotReady -> currentState.continuation.resume(Unit)
            else -> {}
        }
    }

    /**
     * 协程外部调用的
     */
    override fun hasNext(): Boolean {
        println("hasNex")
        resume()
        return state != State.Done
    }


    /**
     * 协程外部调用的
     */
    override fun next(): T {
        println("next")
        return when (val currentState = state) {
            is State.NotReady -> {
                resume()
                return next()
            }
            is State.Ready<*> -> {
                state = State.NotReady(currentState.continuation)
                (currentState as State.Ready<T>).nextValue
            }
            State.Done -> throw IndexOutOfBoundsException("No value left.")
        }
    }

    override fun resumeWith(result: Result<Any?>) {
        state = State.Done
        result.getOrThrow()
    }

}

interface GeneratorScope<T> {
    suspend fun yield(value: T)
}

fun <T> generator(block: suspend GeneratorScope<T>.(T) -> Unit): (T) -> Generator<T> {
    return { parameter: T ->
        GeneratorImpl(block, parameter)
    }
}

fun main() {
    //generator返回一个函数，调用这个函数才会残生Generator
    val nums = generator { start: Int ->
        for (i in 0..5) {
            yield(start + i)
        }
    }
    val seq = nums(10)

    /**
     * 用for in 语句不够直观，这里改写一下
     */
    val iterator = seq.iterator()
    while (iterator.hasNext()) {
        println(iterator.next())
    }


    Thread.sleep(10000)
/*    val sequence = sequence {
        yield(1)
        yield(2)
        yield(3)
        yield(4)
        yieldAll(listOf(1, 2, 3, 4))
    }

    for (element in sequence) {
        println(element)
    }

    val fibonacci = sequence {
        yield(1L) // first Fibonacci number
        var current = 1L
        var next = 1L
        while (true) {
            yield(next) // next Fibonacci number
            next += current
            current = next - current
        }
    }

    fibonacci.take(10).forEach(::println)*/
}