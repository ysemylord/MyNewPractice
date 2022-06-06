package coroutine_demo.classes.ch05

import common.log
import coroutine_demo.classes.ch05.async
import coroutine_demo.classes.ch05.scope.GlobalScope
import kotlin.concurrent.thread

class AsyncTest {
}

suspend fun main() {
    GlobalScope.launch {
        log("1")
        val deffered = async {
            log("2")
            delay(1000)
            log("3")
            "结果"
        }
        log("4")
        val res = deffered.await()
        println(res)
    }
}