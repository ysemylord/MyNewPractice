package com.kotlin_demo.function_demo

/**
 * 函数的几种表现形式
 *
 * 1.函数的格式
 * fun isBigNum(num:Int):Boolean{
 *    return num>1000
 * }
 *
 * 2.函数类型的格式
 * 参数列表 -> 返回值
 * (Int)-> Boolean
 * (Int)-> Unit
 *
 * isBigNum 的函数类型就是
 * (Int)->Boolean
 *
 *
 * 2. 匿名函数
 * fun(num:Int):Boolean{
 *
 * }
 *
 * 3.声明一个函数类型的变量，并指向一个匿名函数
 * val isBigNum:(Int)->Boolean = fun(num:Int){
 *     return num>100
 * }
 *
 * 4.使用lambda表达式简化匿名函数
 * val isBigNum: (Int) -> Boolean = { num ->
 *     num > 100
 *  }
 *  或者
 *
 *val isBigNum = { num:Int ->
 *        num > 100
 *}
 *
 *5. 函数字面常量
 *  匿名函数和lambda表达式都是函数字面常量
 *
 * 6.函数体和lambda表达式的区别
 *
 * 代码块函数体
 *  fun isBigNum(num:Int):Boolean{
 *      return num>100
 *  }
 *
 *  单表达式函数体
 *  fun isBigNum(num:Int)=num>100
 *
 *  lambda表达式
 *
 *  val isBigNum={ num:Int -> num>100 }
 *
 */
fun main() {


}