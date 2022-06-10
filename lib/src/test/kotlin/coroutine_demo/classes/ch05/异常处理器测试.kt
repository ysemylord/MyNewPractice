package coroutine_demo.classes.ch05

import common.log
import coroutine_demo.classes.ch04.js.myAsync0
import coroutine_demo.classes.ch05.exception.CoroutineExceptionHandler
import coroutine_demo.classes.ch05.scope.GlobalScope
import org.junit.Test

class 异常处理器测试 {
    @Test
    fun test() {
        runBlocking {
            val def = async {
                Thread.sleep(200)
                throw Exception("error")
                "100"
            }
            try {
                val res = def.await()
                log(res + "")
            }catch (e:Exception){
                log("发生异常")
            }
        }
    }
}

fun main() {

    val exceptionHandler = CoroutineExceptionHandler { context, e ->
        log(e.message!!)
    }

    GlobalScope.launch {
        GlobalScope.launch(exceptionHandler) {
            log("1")
            throw Exception("error")
            log("2")
        }
        delay(2000)
        log("3")
    }

    Thread.sleep(20000)
}