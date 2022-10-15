package ch03

import log
import org.junit.Test
import kotlin.coroutines.*

/**
 * 协程的启动
 */
class Ch031 {


    class MyCompletion(override val context: CoroutineContext = EmptyCoroutineContext) :
        Continuation<Int> {
        override fun resumeWith(result: Result<Int>) {
            "Coroutine End:$result".log()
        }

    }

    @Test
    fun one() {
        val continuation = suspend {
            "1".log()
            3
        }.createCoroutine(MyCompletion())

        continuation.resume(Unit)

        Thread.sleep(10000000000000)
    }

    private fun func(s: Int, block: Int.() -> Int) {
        block(s)
    }

    @Test
    fun two() {

        val f0 = { p1: Int ->
            1 + p1
        }
        println(f0(1))

        val f1: Int.(p1: Int) -> Int = { p1 ->
            //下面这两行时等价的，调用Receiver的方法
            1 + p1 + this.toFloat().toInt()
            1 + p1 + toFloat().toInt()
        }

        val receiver = 1
        println(f1(receiver,2))

        Thread.sleep(10000000000000)
    }
}