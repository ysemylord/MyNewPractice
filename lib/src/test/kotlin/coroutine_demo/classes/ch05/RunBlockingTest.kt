package coroutine_demo.classes.ch05

import common.log
import kotlin.coroutines.EmptyCoroutineContext

class RunBlockingTest {
}

fun main()
    //协程体一共有几次resumeWith
    //启动协程时1次
   = runBlocking {
        log("1")
        val job = launch {
            log("2")
            delay(2000)
            log("3")
        }
        log("4")
        job.join()//这里会挂起恢复，这里1次
        log("5")
        delay(2000)//这里会挂起恢复，这里1次
        log("6")
    }
