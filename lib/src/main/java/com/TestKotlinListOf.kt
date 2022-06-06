package com

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


fun main() {
    val mutableList = mutableListOf<String>()
    val listOfEmpty = listOf<String>()
    val listOfNotEmpty = listOf<String>("1","2")
    println(mutableList::class.java.name)
    println(listOfEmpty::class.java.name)
    println(listOfNotEmpty::class.java.name)
   GlobalScope.launch {
       async {  }
   }
}

class Test {
}