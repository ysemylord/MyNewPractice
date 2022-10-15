import kotlinx.coroutines.*
import org.junit.Test

class DispatchTest {

    /**
     * 调度器
     */
    @Test
    fun one() {

        Thread.currentThread().name = "main"

        "0".log()

        GlobalScope.launch {//没有指定调度器，而且启动的是顶层协程，则启动的顶层协程使用默认的调度器Dispatchers.Default
            "1".log()
        }

        GlobalScope.launch(Dispatchers.Default) {
            "2".log()
        }

        GlobalScope.launch(Dispatchers.IO) {
            "3".log()
        }



        Thread.sleep(1000000000000000000)
    }


    @Test
    fun two() {
        val newSingleThreadContext = newSingleThreadContext("single thread")

        GlobalScope.launch(newSingleThreadContext) {
            "1".log()
            Thread.sleep(5000)
            "2".log()
        }

        GlobalScope.launch(newSingleThreadContext) {
            "3".log()
        }

        Thread.sleep(1000000000000000000)
    }

    /**
     *
     */
    @Test
    fun three() {
        val newSingleThreadContext = newSingleThreadContext("single thread")

        GlobalScope.launch(newSingleThreadContext) {
            "1".log()
            delay(5000)
            "2".log()
        }

        GlobalScope.launch(newSingleThreadContext) {
            "3".log()
        }

        Thread.sleep(1000000000000000000)
    }


}