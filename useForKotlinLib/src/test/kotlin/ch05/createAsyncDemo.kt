package ch05

import kotlinx.coroutines.*
import log
import org.junit.Test

class createAsyncDemo {
    @Test
    fun asyncDemo() {

        val job = GlobalScope.launch {
            val deferred = async {
                1212
            }
            delay(1000)
            deferred.await().toString().log()
        }

       Thread.sleep(100000)
    }

    @Test
    fun withContextDemo(){
        val result = GlobalScope.launch {
            val deferred = withContext(coroutineContext) {
                1212
            }


        }

        Thread.sleep(100000)
    }
}