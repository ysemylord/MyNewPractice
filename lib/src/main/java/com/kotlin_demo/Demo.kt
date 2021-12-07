package com.kotlin_demo


fun main() {
   val student = Student()
    val name:String? = student.name
    print(name?.toString())
}