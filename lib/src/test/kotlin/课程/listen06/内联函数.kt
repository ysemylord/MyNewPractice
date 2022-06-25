package 课程.listen06

import org.junit.Test

class 内联函数 {

    @Test
    fun test0(){
        val list = listOf(1,2,3,4)
         list.forEach {
             println("start $it")
             if(it==3){return@forEach}
             println("end $it")
         }

        println("end")
        while (true){}
    }
}

private inline fun cost(block: () -> Unit) {
    val t = System.currentTimeMillis()
    block()
    println("${System.currentTimeMillis() - t}")
}

fun main() {
    cost {
        for (i in 1..10) {
            println(i)
        }
    }
}