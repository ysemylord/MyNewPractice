package ch03

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import log
import org.junit.Test
import kotlin.coroutines.*
import kotlin.random.Random

fun f(block:()->Unit){

}

class Ch031CreateCoroutineMore {

    class MyScope {
        fun produceNum(): Int {
            return Random(1).nextInt()
        }
    }


    @Test
    fun one() {

        //我们不能直接声明一个带有Receiver的suspend{}，所以

        val suspendLambdaWithReceiver: suspend MyScope.() -> Int = {
            "协程体执行".log()
            this.produceNum()
        }

        val continuation =
            suspendLambdaWithReceiver.createCoroutine(MyScope(), Ch031.MyCompletion())

        continuation.resume(Unit)

        Thread.sleep(10000000000000)
    }


}