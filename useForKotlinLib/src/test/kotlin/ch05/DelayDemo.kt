package ch05

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DelayDemo {

}

suspend fun main() {
    GlobalScope.launch {
            delay(2000)
    }
    Thread.sleep(1000000000)

}