package coroutine_demo.classes.ch01

import org.junit.Test
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class BaseApi {
    class TestBitmap(val url: String)

    @Test
    fun useIterableMap() {
        val urls = listOf("url1", "url2", "url3")
        val bitmaps = urls.map {
            TestBitmap(it)
        }
    }

    @Test
    fun useFuture() {
        val executor = Executors.newCachedThreadPool()
        val future = executor.submit<String> {
            Thread.sleep(1000)
            "result"
        }
        val result = future.get()//主流程阻塞在这里了
        println(result)
    }

    @Test
    fun useCompletableFuture() {
        println("thread0 ${Thread.currentThread()}")
        val completableFuture = CompletableFuture.supplyAsync {
            Thread.sleep(1000)
            println("thread1 ${Thread.currentThread()}")
            "1"
        }.thenAccept {
            println("thread2 ${Thread.currentThread()}")
            println(it)
        }
        println("2")

        completableFuture.join()
    }

    @Test
    fun useCompletableFutureAll() {
        val completableFuture1 = CompletableFuture.supplyAsync {
            Thread.sleep(2000)
            println("1")
            "1"
        }

        val completableFuture2 = CompletableFuture.supplyAsync {
            Thread.sleep(1000)
            println("2")
            "2"
        }

        val completableFuture = CompletableFuture.allOf(completableFuture1, completableFuture2)
            .thenApply {
                //completableFuture1和completableFuture2都完成时调用
                println("3")
            }

        completableFuture.join()
    }
}