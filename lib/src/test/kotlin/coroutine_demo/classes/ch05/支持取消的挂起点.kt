package coroutine_demo.classes.ch05


import coroutine_demo.classes.ch05.scope.GlobalScope
import kotlinx.coroutines.suspendCancellableCoroutine
import org.junit.Test
import java.util.concurrent.TimeUnit

class 支持取消的挂起点 {
    @Test
    fun Test() {
        val job = GlobalScope.launch {
            println("1")
           suspendCancellableCoroutine<Unit> {
               println("111")
               println("111")
               println("111")
               println("111")
               println("111")
           }
            println("2")
            delay2(2, TimeUnit.SECONDS)
            println("3")
        }
        job.invokeOnCompletion {
            println("完成")//注意一点，取消的时候，完成回调也会调用
        }
        job.cancel()//因为这里cancel掉了，所以"2"不会打印了
        Thread.sleep(20000)
    }
}