package com.kotlin_demo.function

/**
 * 扩展函数
 */
fun main() {
    val value = 2
    print(value.addTwo())
}

/**
 * 格式
 * fun Receiver.函数名(参数列表):返回值{
 *    函数体
 * }
 *
 * 函数体内部可以使用Receiver的方法
 */
fun Int.addTwo(): Int {
    return this.plus(2)
}