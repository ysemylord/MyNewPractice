package 课程.listen06

import org.junit.Test
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class SAMKotlin {
    @Test
    fun test() {

        SAMJava.testSAM {
            println()
        }

        test {
            println()
        }
    }
}


private fun test(block: () -> Unit) {

}

fun main(args: Array<String>) {
    val executor: Executor = Executors.newSingleThreadExecutor()
    executor.execute { println("run in executor") }

    executor.execute(
        object : Runnable {
            override fun run() {
                println("run in executor") //{ println("run in executor") }.invoke()
            }
        })


}
