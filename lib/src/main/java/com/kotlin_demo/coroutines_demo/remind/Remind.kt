package com.kotlin_demo.coroutines_demo.remind

import kotlinx.coroutines.*

/**
 * 需求
 * 两个任务异步执行，等待两个任务都有结果后，再继续执行
 */

fun main() {
    // remind1()
    // remind2()
    runBlocking {
        val task1= async {
            delay(2000)
            2
        }

        val task2 = async {
            delay(1000)
            2
        }

        println(task1.await()+task2.await())
    }
}

private fun remind2() {
    runBlocking {

        print("step1")
        //witch context会使当前协程挂起
        val res1 = withContext(Dispatchers.IO) {
            delay(3000)//模拟延时任务
            1
        }

        //上面的任务执行完毕后，才继续向下执行

        print("step2")
        val res2 = withContext(Dispatchers.IO) {
            delay(3000)//模拟延时任务
            1
        }

        println("step3")
        println(res1 + res2)

    }
}

private fun remind1() {
    runBlocking {
        //launch的任务是异步的，任务开启后，继续向下执行
        val job1 = launch(Dispatchers.IO) {
            delay(1000)//模拟延时任务
        }

        val job2 = launch(Dispatchers.IO) {
            delay(1000)//模拟延时任务
        }

        //等待任务1，2完成，但是没有返回值
        job1.join()
        job2.join()
    }
}
