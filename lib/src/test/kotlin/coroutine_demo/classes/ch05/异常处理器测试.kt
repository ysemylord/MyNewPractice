package coroutine_demo.classes.ch05

import common.log
import kotlinx.coroutines.*
import org.junit.Test


class 异常处理器测试 {

    @Test
    fun testLaunch0() {

        GlobalScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            log("CoroutineExceptionHandler 1")
        }) {
            val i = 1 / 0
        }

        Thread.sleep(20000)
    }

    @Test
    fun testLaunch1() {
        GlobalScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            log("CoroutineExceptionHandler 1")
        }) {
            launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                log("CoroutineExceptionHandler 2")
            }) {
                val i = 1 / 0
            }
        }
        Thread.sleep(20000)
    }

    @Test
    fun testAsync() {
        GlobalScope.launch(CoroutineExceptionHandler { context, e ->
            log("CoroutineExceptionHandler1 $e")
        }) {
            val def = async(CoroutineExceptionHandler { context, e ->
                log("CoroutineExceptionHandler2")
            }) {
                throw Exception("error")
                "100"
            }
            try {
                val res = def.await()
                log(res + "")
            } catch (e: Exception) {
                log("Exception Handle 3")
            }
            delay(3000)
            log("2")
        }
        Thread.sleep(20000)
    }


    @Test
    fun testWithContext0() {
        GlobalScope.launch(CoroutineExceptionHandler { context, e ->
            log("CoroutineExceptionHandler1")
        }) {

            val res = withContext(CoroutineExceptionHandler { context, e ->
                log("CoroutineExceptionHandler2")
            }) {
                throw Exception("WithContext")
                "100"
            }

            try {
                log(res)
            } catch (e: Exception) {
                log("WithContext")
            }

        }
        Thread.sleep(20000)
    }

    @Test
    fun testWithContext1() {
        GlobalScope.launch(CoroutineExceptionHandler { context, e ->
            log("CoroutineExceptionHandler1")
        }) {
            try {
                val res = withContext(CoroutineExceptionHandler { context, e ->
                    log("CoroutineExceptionHandler2")
                }) {
                    throw Exception("WithContext")
                    "100"
                }

                log(res)
            } catch (e: Exception) {
                log("WithContext")
            }

        }
        Thread.sleep(20000)
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