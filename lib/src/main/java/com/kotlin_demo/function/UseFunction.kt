package com.kotlin_demo.function

/**
 * 函数是一个类型，可以像String,int,float,class 一样使用
 * 声明Int变量         var i:Int
 * 声明String变量      var s:String
 * 声明User对象        var user:User
 * 声明函数类型的变量    var f:(String)->String
 */

fun main() {

    //声明函数，并使用
    val fixName: (String) -> String = { name ->
        name + "111"
    }
    val message = convertName("jack", fixName)

    //匿名函数
    val message2 = convertName("lord") { name ->
        name + "111"
    }

    //使用对象中的函数
    val useFunction = UseFunction()
    val message3 = convertName("mary", useFunction::methodInClass)


    println(message)
    println(message2)
    println(message3)
}

fun convertName(name: String, fixName: (String) -> String): String {
    val result = fixName(name)
    return "$result by convert"
}

class UseFunction {
    //在类中的方法
    fun methodInClass(name: String): String {
        return name + "111"
    }
}