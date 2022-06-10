package coroutine_demo

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SuspendCoroutineAndSafeContinuation {
    @Test
    fun test1() {
        GlobalScope.launch {
            val res = suspendCoroutine<Int> { continuation ->
                100
                continuation.resume(100)
            }
        }
    }

}