package coroutine_demo.classes

import org.junit.Test
import java.util.concurrent.Executors
import kotlin.coroutines.*
import kotlin.random.Random

/**
 * 复杂调度
 */
class Class4ComplexDispatch {


    class Generator {
        var value = 0
        var tempContinuation: Continuation<Unit>? = null
        val continuation: Continuation<Unit>

        init {
            continuation = suspend {
                while (true) {
                    suspendCoroutine<Unit> { continuation ->
                        tempContinuation = continuation
                        val randomNum = Random.nextInt()
                        value = randomNum
                    }
                }
                Unit
            }.createCoroutine(object : Continuation<Unit> {
                override val context: CoroutineContext
                    get() = EmptyCoroutineContext

                override fun resumeWith(result: Result<Unit>) {
                    println("结果 $result")
                }
            })
        }


        fun start(getResult: (Int) -> Unit) {
            continuation.resume(Unit)
            while (true) {
                Thread.sleep(2000)
                tempContinuation?.resume(Unit)
                getResult(value)
            }
        }
    }


    @Test
    fun test() {
        val generator = Generator()
        generator.start {
            println(it)
        }
    }
}