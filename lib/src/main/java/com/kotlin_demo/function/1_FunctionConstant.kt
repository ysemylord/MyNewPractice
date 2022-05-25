package com.kotlin_demo.function

/**
 * 函数字面值
 * 1. lambda表达式
 * 2. 匿名函数
 *
 */

fun main() {
    //lambada 函数字面常量
    val fun1 = { one: Int, two: Int -> one + two }
    println(fun1(1, 2))

    //匿名函数 函数字面常量
    val fun2 = fun(one: Int, two: Int): Int {
        return one + two
    }
    println(fun2(1,3))
}