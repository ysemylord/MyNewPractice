package coroutine_demo.classes.ch05

import com.kotlin_coroutines.lanch
import com.kotlin_demo.coroutines_demo.scope_demo.log
import common.Dispatchers.Single
import common.SingleDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.*

class Test {
    @Test
    fun test() {
        val job = coroutine_demo.classes.ch05.scope.GlobalScope.launch(Single) {
            log("1")
           launch(Single) {
                log("2")
               // kotlinx.coroutines.delay(3000)
                log("3")
            }
            log("4")
            log("5")
            log("6")
            log("7")
            log("8")

        }

        Thread.sleep(20000)
    }

    @Test
    fun test2(){
        suspend {
            log("1")
            suspend {
                log("1111")
            }.startCoroutine(object :Continuation<Unit>{
                override val context: CoroutineContext
                    get() = EmptyCoroutineContext

                override fun resumeWith(result: Result<Unit>) {
                }

            })
            log("2")
            log("3")

        }.startCoroutine(object :Continuation<Unit>{
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Unit>) {
            }

        })

        Thread.sleep(3000)
    }
}

suspend fun main() {

    val job = GlobalScope.launch {
        suspendCoroutine<Unit> {
            thread {
                println("start")
                Thread.sleep(4000)
                println("end")
                it.resume(Unit)
            }
        }
    }
    val disposable1 = job.invokeOnCompletion {
        println("1")
    }

    val disposable2 = job.invokeOnCompletion {
        println("2")
    }


    /*       disposable1.dispose()
           disposable2.dispose()*/

    //job.join()
    /**
     *  join()类似于
     */
    suspendCoroutine<Unit> { continuation ->
        job.invokeOnCompletion {
            continuation.resume(Unit)
        }
    }

    println("3")

}