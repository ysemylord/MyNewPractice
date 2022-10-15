package ch04

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import log
import kotlin.coroutines.*

/**
 * 简单实现一个对称协程的范例
 * 不抽就像出框架，直接硬干一个调度流程
 * main->to->main->t1
 */


private fun getCompletion() = object : Continuation<Unit> {
    override val context: CoroutineContext
        get() = EmptyCoroutineContext

    override fun resumeWith(result: Result<Unit>) {
    }
}


class TestCoroutine(
    var currentContinuation: Continuation<Unit>? = null,
    block: suspend MyScope.() -> Unit
) {
    init {
        currentContinuation = block.createCoroutine(object : MyScope {
            public override suspend fun yield() {
                suspendCoroutine<Unit> {
                    currentContinuation = it
                }
            }
        }, getCompletion())
    }

    fun resume() {
        currentContinuation?.resume(Unit)
    }


}

//使用作用域存储协程自身的信息
interface MyScope {
    suspend fun yield()
}

fun main() {

    var main: TestCoroutine? = null

    val t0 = TestCoroutine {
        "t0协程运行了一会".log()
        "把运行权交给main,自己挂起".log()
        main?.resume()
        yield()
        "t0 2".log()
    }

    val t1 = TestCoroutine {
        "t1 执行".log()
        yield()
        "2".log()
    }

    main = TestCoroutine(null) {

        "main 把运行权限交给t0,自己挂起".log()

        t0.resume()//t0执行

        "main 继续执行，然后将运行权限交给t1,main挂起".log()

        t1.resume()//t1执行

        yield()//main挂起

    }
    main.resume()

    Thread.sleep(20000)


}



