package ch05

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import log
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DispatchDemo {
    @Test
    fun one() {
        GlobalScope.launch {
            "111".log()
            t()
            "222".log()
            t()
            "222".log()
            t()
            "222".log()
            t()
            "222".log()
        }
        Thread.sleep(10000000)
    }

    private suspend fun t() {
        suspendCoroutine { continuation ->
            thread {
                Thread.sleep(2000)
                continuation.resume(Unit)
            }
        }
    }
}