package ch03

import log
import org.junit.Test
import kotlin.coroutines.*


suspend fun suspendFun0(): Int {
    ("in coroutine").log()
    return 0
}

class Ch03 {

    private fun getCompletion(): Continuation<Int> = object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            "Coroutine End:$result".log()
        }
    }

    /**
     * 通过suspend函数创建协程
     */
    @Test
    fun one() {

        //使用suspend函数的几种形式

        //使用::引用suspend函数
        val func0 = ::suspendFun0

        //通过 suspend(noinline block: suspend () -> R): suspend () -> R = block
        //将lambda转化为suspend函数
        val func1 = suspend {
            ("in coroutine").log()
            1
        }

        //声明suspend函数类型的变量，并赋值
        val fun2: suspend () -> Int = {
            ("in coroutine").log()
            2
        }

        func0.createCoroutine(getCompletion()).resume(Unit)

        (::suspendFun0).createCoroutine(getCompletion()).resume(Unit)

        func1.createCoroutine(getCompletion()).resume(Unit)

        fun2.createCoroutine(getCompletion()).resume(Unit)

        Thread.sleep(10000000)
    }

    //协程体重出现异常
    @Test
    fun two() {
        suspend {
            1 / 0
        }.createCoroutine(getCompletion()).resume(Unit)
    }
}