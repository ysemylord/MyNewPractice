package com.kotlin_demo.function

/**
 * 带有接收者的函数类型
 */


fun main() {
    //声明一个带有receiver的函数(使用lambda表达式)
    //在函数题内部可以使用receiver的函数
    val sum: Int.(Int) -> Int = { other -> plus(other) }
    //调用带有receiver的函数
    val result = sum(2, 1)
    println(result)

    //声明一个带有receiver的函数(使用匿名函数)
    val sum2 = fun Int.(other: Int): Int {
        return this.plus(other)
    }
    println(sum2(2, 2))

    //匿名函数是可以直接声明Receiver的
    //但是lambda表达式不能直接声明带Receiver
    /**
     * 下面这样是不行的
     * Int.{ other -> plus(other) }
     */
    //但是，Receiver如果可以从上下文判断出来
    //那么lambda表达式可以作为带接受者的函数字面量
    html {       // 带接收者的 lambda 由此开始
        body()   // 调用该接收者对象的一个方法
    }
}

class HTML {
    fun body() {
        println("body")
    }
}

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()  // 创建接收者对象
    html.init()        // 将该接收者对象传给该 lambda
    return html
}

