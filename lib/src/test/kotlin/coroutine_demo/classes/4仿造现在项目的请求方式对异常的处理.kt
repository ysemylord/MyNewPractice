package coroutine_demo.classes

import com.kotlin_demo.coroutines_demo.scope_demo.log
import common.githubApi
import kotlinx.coroutines.*
import org.junit.Test
import retrofit2.await
import kotlin.concurrent.thread
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class `4仿造现在项目的请求方式` {
    @Test
    fun test0() {
        GlobalScope.launch {
            val res = withContext(Dispatchers.IO) {
                try {
                    thread {
                        log("子线程")
                        throw Exception("异常")
                    }
                    //这样子是捕获不到异常的，因为子线程的代码运行在另一个任务栈中，捕获不了
                } catch (e: Exception) {
                    log("发生异常了")
                    log(e.message ?: "")
                }
            }
        }
        Thread.sleep(20000)
    }

    @Test
    fun test1() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    suspendCoroutine<Unit> { continuation ->
                        thread {
                            try {
                                log("子线程")
                                throw Exception("异常")
                                //在子线程内部将异常捕获到，
                                // 恢复协程并在挂起点抛出异常
                            } catch (e: Exception) {
                                continuation.resumeWithException(e)
                            }
                        }
                    }
                } catch (e: Exception) {
                    log("发生异常了")
                    log(e.message ?: "")
                }
            }
        }
        Thread.sleep(20000)
    }

}

class R(val data: Any?, val success: Boolean, val msg: String = "")

fun main() {
    GlobalScope.launch {
        val res = withContext(Dispatchers.IO) {
            try {
                val call = githubApi.getUserCallback("bennyhuo")
                val res = call.await()
                R(res, false)
            } catch (e: Exception) {
                e.printStackTrace()
                R(null, false, e.message ?: "")
            }
        }
        res.data?.run {
            log(this.toString())
        } ?: run {
            log(res.msg)
        }
    }
    Thread.sleep(20000)
}