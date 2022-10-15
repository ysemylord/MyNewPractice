package coroutine_demo.classes.ch01

import org.junit.Test
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

/**
 * 复杂分支
 */
class ComplexBranch {



    //同步循环
    @Test
    fun `syncLoop`() {
        val urls = listOf<String>("url1", "url2")
        val bitmaps = mutableListOf<TestBitmap>()
        urls.forEach {
            bitmaps.add(syncBitmap(it))
        }
        println("获取到所有的bitmap $bitmaps")
    }

    //异步循环
    //因为在循环里面是一个异步任务，必须使用额外的工具保证异步任务全部结束
    @Test
    fun `asyncLoop`() {
        val urls = listOf<String>("url1", "url2")
        val bitmaps = mutableListOf<TestBitmap>()
        val countDownLatch = CountDownLatch(urls.size)
        urls.forEach {
            asyncBitmap(it) { bitmap ->
                bitmaps.add(bitmap)
                countDownLatch.countDown()
            }
        }
        countDownLatch.await()//在这里等待结果
        println("获取到所有的bitmap $bitmaps")
    }

}

