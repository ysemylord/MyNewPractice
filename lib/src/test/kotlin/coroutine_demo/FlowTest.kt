package coroutine_demo

import common.log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class FlowTest {

    @Test
    fun testCollection() {


        listOf<Int>(1, 2, 3)
            .map { it + 2 }//遍历一次
            .filter { it > 2 }//遍历一次
            .forEach {
                log(it.toString())
            }
    }

    @Test
    fun testSequence() {
        listOf<Int>(1, 2, 3).asSequence()
            //只遍历一次
            .map { it + 2 }
            .filter { it > 2 }
            .forEach {
                log(it.toString())
            }
    }

    @Test
    fun testFlow() {
        val exeutor1 = Executors.newSingleThreadExecutor { run ->
            Thread(run).also {
                it.name = "run blocking thread"
            }
        }

        val dispatcher1 = object : CoroutineDispatcher() {
            override fun dispatch(context: CoroutineContext, block: Runnable) {
                exeutor1.execute(block)
            }
        }

        val exeutor2 = Executors.newSingleThreadExecutor { run ->
            Thread(run).also {
                it.name = "flow on thread"
            }
        }

        val dispatcher2 = object : CoroutineDispatcher() {
            override fun dispatch(context: CoroutineContext, block: Runnable) {
                exeutor2.execute(block)
            }
        }
        runBlocking(dispatcher1) {
            val flow = flow<Int> {
                log("start")
                emit(1)
                delay(1000)
                emit(2)
                emit(3)
                log("end")
            }.catch {
                log(this.toString())
            }.onCompletion {
                log("finally")
            }
            flow.flowOn(dispatcher2).collect {
                log(it.toString())
            }
        }
    }

    fun simple(): Flow<String> =
        flow {
            for (i in 1..3) {
                println("Emitting $i")
                emit(i) // emit next value
            }
        }.map { value ->
            check(value <= 1) { "Crashed on $value" }
            "string $value"
        }

    @Test
    fun testException() {
        runBlocking {
            simple()
                .onEach {
                    println(it)
                }
                .onCompletion { e ->
                    println("onCompletion $e")
                }//onCompletion要放在catch前面
                .catch { e -> println("exception $e") }//catch只能捕获上流异常
                .collect()
        }

        Thread.sleep(200000)
    }
}

suspend fun main() {
    val flow = flow<Int> {
        emit(1)
        delay(1000)
        emit(2)
        emit(3)
    }
    flow.collect {
        log(it.toString())
    }
}