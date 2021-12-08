package com.gson_demo

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

data class Result(val code: Int, val message: String, val data: LicenseBean)

data class LicenseBean(
    val key: String,
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
    print(sb.toString())
    val bean = Gson().fromJson(sb.toString(), Result::class.java)
    //      String-----------Type
    //                |
    //           TypeAdapter
    //                |
    //               \|/
    //               Bean
    print("bean:\n $bean")
}