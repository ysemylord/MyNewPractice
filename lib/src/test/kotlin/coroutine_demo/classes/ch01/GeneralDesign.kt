package coroutine_demo.classes.ch01

import com.kotlin_coroutines.Bitmap
import org.junit.Test
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.Future

class GeneralDesign {
    val ioExecutor = Executors.newCachedThreadPool()

    private fun bitmapFuture(url: String): Future<TestBitmap> {
        return ioExecutor.submit<TestBitmap> {
            Thread.sleep(1000)
            TestBitmap(url)
        }
    }

    @Test
    fun useFuture() {
        val bitmaps = listOf("url1", "url2")
            .map { url ->
                bitmapFuture(url)
            }.map { future ->
                future.get()//同步阻塞，等待异步结果
            }
        println(bitmaps)
    }


    private fun bitmapCompletableFuture(url: String): CompletableFuture<TestBitmap> {
        return CompletableFuture.supplyAsync {
            Thread.sleep(1000)
            TestBitmap(url)
        }
    }

    @Test
    fun useCompletableFuture() {
        val urls = listOf("url1", "url2")
        val completableFutures = urls.map {
            bitmapCompletableFuture(it)
        }

        //为了知道所有任务完成，调用allOf通过所有任务得到一个completableFuture
        val completableFuture = CompletableFuture.allOf(*completableFutures.toTypedArray())
            .thenApply {
                //所有future执行完成

                //获取素有feature的结果,并将其转化为一个Bitmap数组
                completableFutures.map {
                    it.get()
                }
            }

        completableFuture.thenApply {
            println("完成")
        }

        println("end")

        completableFuture.join()


    }
}