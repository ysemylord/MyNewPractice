package ch04

import org.junit.Test
import kotlin.coroutines.*

/**
 *
 */


/**
 *一个状态机的简单例子
 * 三种状态 NotReady，Ready,Done
 * 状态转化如下
 *     NotReady ---->Deon
 *      /\   |
 *      |   \/
 *     Ready
 */
class StateMachineDemoTwo {

    sealed class State {
        class NotReady : State()
        class Ready : State()
        object Done : State()
    }

    var currentState: State = State.NotReady()
    var continuation: Continuation<Unit>? = null
    suspend fun toReady() {
        return suspendCoroutine<Unit> {
            this.continuation = it
            when (currentState) {
                is State.NotReady -> {
                    println("to ready")
                    currentState = State.Ready()
                }
                is State.Ready -> {
                    println("Ready 状态,不能再次Ready")
                }
                is State.Done -> {
                    println("Done状态，不能Done了")
                }
            }
        }

    }

    fun toNotReady() {
        when (currentState) {
            is State.Ready -> {
                println("to not ready")
                currentState = State.NotReady()
            }
            is State.NotReady -> {
                println("NotReady 状态,不能再次NotReady")
            }
            is State.Done -> {
                println("Done状态，不能NotReady了")
            }
        }
    }

    fun toDone() {
        when (currentState) {
            is State.Ready -> {
                println("Ready状态不能Deon")
            }
            is State.NotReady -> currentState = State.Done
            is State.Done -> {
                println("Done状态，不能Done了")
            }
        }
    }

    /**
     * 把上一个状态机改一下
     * 改成
     * 协程：toReady,然后挂起
     * 主流程：toNoeReady,然后恢复协程
     *
     * 以此循环
     */
    @Test
    fun one() {
        val stateMachineDemo = StateMachineDemoTwo()
        val continuation = suspend {
            while (true) {
                stateMachineDemo.toReady()
            }
        }.createCoroutine(object : Continuation<Unit> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Unit>) {

            }

        })

        //创建协程时的SafeContinuation
        continuation.resume(Unit)
        stateMachineDemo.toNotReady()

        //如果我们想要恢复协程，不能使用之前的SafeContinuation,
        // 要使用表示挂起点的continuation

        while (true) {
            //每个挂起点的continuation
            stateMachineDemo.continuation?.resume(Unit)
            stateMachineDemo.toNotReady()
        }


        Thread.sleep(100000)

    }
}

