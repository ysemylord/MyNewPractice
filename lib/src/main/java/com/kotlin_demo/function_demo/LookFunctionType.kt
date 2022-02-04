package com.kotlin_demo.function_demo

import kotlinx.coroutines.runBlocking

/**
 * 查看函数类型
 */

fun a0() {

}

suspend fun a1() {

}

fun b0(): Int {
    return 1
}

suspend fun b1(): Int {
    return 1
}

fun Int.c0(block: (Int) -> String): String {
    return "1"
}

fun c00(i:Int,block: (Int) -> String): String {
    return "1"
}

suspend fun Int.c1(block: (Int) -> String): String {
    return "1"
}

suspend fun c11(i: Int, block: (Int) -> String): String {
    return "1"
}

fun x(block: suspend Int.((Int) -> String) -> String) {
    runBlocking {

        val receiver = 10
        val res1 = block(receiver) {
            it.toString()
        }

        val res2 = receiver.block {
            it.toString()
        }

        //receiver的两种写法
    }
}

fun main() {
    val typeA0 = ::a0  // ()->Unit
    val typeA1 = ::a1 //suspend()->Unit
    val typeB0 = ::b0 //()->Int
    val typeB1 = ::b1 //suspend()->Int
    val typeC0 = Int::c0 //  Int.((Int)->String)->String  本质:(Int,(Int)->String)->String
    val typeCX = 1::c0  //((Int)->String) ->String
    val typeC00 = ::c00 // (Int,(Int)->String)->String
    val typeC1 = Int::c1 // suspend Int.((Int) -> String ) -> String
    val typeC11 = ::c11 // suspend (Int , (Int)->String) -> String
}