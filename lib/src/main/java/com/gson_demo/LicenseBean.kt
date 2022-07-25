package com.gson_demo

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

/**
 * 对数据类 data class 来说,反序列化时
 *
 * 1. 基本数据类型有默认值(Boolean 默认为false,Int 默认为0)
 * 2. 设置的默认值是没用的
 * 3. 设置的非空类型是没用的
 *
 * 1. json字符串中没有male这个字段，male的值为默认值false
 * 2. json字符串中没有female这个字段，female设置了默认值为true，但是反序列化后，其值为false
 * 3. json字符串中没有key这个字段,key设置了默认值，但是反序列化后，其值为null
 *
 * 所以非基础类型的数据都应该设置为可空类型
 */

data class Result(val code: Int, val message: String, val data: LicenseBean)

data class LicenseBean(
    val male: Boolean,
    val female: Boolean = true,
    val key: String = "jack",
    val ids: List<String>,
    val name: String,
    val spdx_id: String,
    val url: String,
    val node_id: String,
    val real: Boolean
)

fun main() {
    baseUse()

    // testEmpty()
}

private fun testEmpty() {
    val sb = StringBuilder()
    val fileInputStream = FileInputStream("gson_empty_string.txt")
    val bufferedReader = BufferedReader(InputStreamReader(fileInputStream))
    var line: String?
    while (bufferedReader.readLine().apply { line = this } != null) {
        sb.append(line).append("\n")
    }
    val result: Result = Gson().fromJson(sb.toString(), Result::class.java)

    print("bean: $result")
    print("bean: ${result.code}")
}

private fun baseUse() {
    val sb = StringBuilder()
    val fileInputStream = FileInputStream("gson_string.txt")
    val bufferedReader = BufferedReader(InputStreamReader(fileInputStream))
    var line: String?
    while (bufferedReader.readLine().apply { line = this } != null) {
        sb.append(line).append("\n")
    }
    //print(sb.toString())
    val bean = Gson().fromJson(sb.toString(), Result::class.java)
    //      String-----------Type
    //                |
    //           TypeAdapter
    //                |
    //               \|/
    //               Bean

    //println(bean.data.real)
    print("bean:\n $bean")
}