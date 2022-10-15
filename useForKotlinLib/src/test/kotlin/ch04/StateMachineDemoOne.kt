package ch04

import org.junit.Test

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
class StateMachineDemoOne {

    sealed class State {
        class NotReady : State()
        class Ready : State()
        object Done : State()
    }

    var currentState: State = State.NotReady()
    fun toReady() {
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
     * 这个例子，就是简单的状态流转
     */
    @Test
    fun one() {
        val stateMachineDemo = StateMachineDemoOne()

        while (true) {
            stateMachineDemo.toNotReady()
            Thread.sleep(1000)
            stateMachineDemo.toReady()
        }
    }
}

