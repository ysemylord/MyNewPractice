package com.kotlin_demo.function_demo

/**
 * 将函数作为参数使用
 */
fun main() {
    val str1 = "jack"
    val str2 = "lord"
    val specificB = { "$str1+$str2" }
    val wrapperB = { specificB() }
    Call(wrapperB)
}

private fun Call(wrapperB: () -> String): Boolean {
    return wrapperB().length > 5
}