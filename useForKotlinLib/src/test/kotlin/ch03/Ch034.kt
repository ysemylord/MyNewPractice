package ch03

import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.*

class Ch034 {
    suspend fun func1(): String {
        return func0()

    }

    suspend fun func2(): String {
        return func0()

    }

    suspend fun func3(): String {
        return func0()

    }

    suspend fun func0(): String {
        return suspendCoroutine<String> {
            thread {
                Thread.sleep(2000)
                it.resume("4")
            }
        }
    }

    @Test
    fun forLookCode() {
        suspend {
            func1()
            func2()
            func2()
        }.startCoroutine(object : Continuation<String> {
            override val context = EmptyCoroutineContext

            override fun resumeWith(result: Result<String>) {

            }

        })
    }
}