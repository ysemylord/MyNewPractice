package 课程.listen06

import org.junit.Test

class 内联函数高阶函数Return {

    //local-return

    inline fun cal(a: Int, b: Int, block: (Int, Int) -> Unit) {
        block(a, b)
    }

    @Test
    fun localReturn1() {
        cal(2, 20) { a, b ->
            if (a < 10) return@cal
            println(a + b)
        }
        println("end")
    }

    @Test
    fun localReturn2() {
        val list = listOf(1, 2, 3, 4)
        list.forEach {
            if (it == 3) {
                return@forEach
            }
        }
    }

    //none-local-return
    fun noneLocalReturn() {
        val list = listOf(1, 2, 3, 4)
        list.forEach {
            if (it == 3) {
                return
            }
        }
    }

    //crossinline
    inline fun Runnable(crossinline block: () -> Unit): Runnable {
        return object : Runnable {
            override fun run() {
                block()
            }
        }
    }

    //oninline

}