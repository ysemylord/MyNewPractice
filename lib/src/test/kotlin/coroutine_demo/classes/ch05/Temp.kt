import com.kotlin_demo.coroutines_demo.scope_demo.log
import coroutine_demo.classes.ch05.myRunBlocking
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch


fun main() {
     GlobalScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
        log("exception")
    }) {
        log("1")
        launch {
            log("2")
            delay(50000)
            log("3")
        }
        val i=1/0
        log("4")
    }
    Thread.sleep(30000)
}



fun main1() {
    val j = myRunBlocking {

        log("location 1")
        log("location 3")

        //job.cancel()
    }
    return j
}