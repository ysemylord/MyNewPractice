package 课程.listen06

import org.junit.Test

class 集合变换与序列 {

    //变化

    //Iterator的map,filter操作
    //饿汉式
    @Test
    fun testIterator() {
        val list = listOf(1, 2, 3, 4)
        val listNew = list
            .filter {
                println("filter $it")
                it % 2 == 0
            }.map {
                val mapedValue = it * 2 + 1
                println("map $mapedValue")
                mapedValue
            }
        println(listNew)
        Thread.sleep(100000)
    }

    //Sequence的变化是懒汉式
    //需要使用forEach才能触发
    @Test
    fun testSequence() {
        val list = listOf(1, 2, 3, 4)
        val listNew = list.asSequence()
            .filter {
                println("filter $it")
                it % 2 == 0
            }.map {
                val mapedValue = it * 2 + 1
                println("map $mapedValue")
                mapedValue
            }.forEach {
               println("for each $it")
            }
        println(listNew)
        Thread.sleep(100000)
    }

    @Test
    fun testFlatMap(){
        //map:将集合中的元素映射为新的元素
        //并返回一个包含新元素的集合
        //这里的res的类型为 List<IntRang>
        val res= listOf(1,2,3,4).map {
            0 until it
        }
        println(res)

        //flatMap:将集合中的每一个元素映射为一个新的集合
        //然后将所有的新集合中的元素放入一个集合中(这就是所谓的平铺)
        val res2= listOf(1,2,3,4).asSequence().flatMap {
            0 until it
        }.joinToString()
        println(res2)

        Thread.sleep(100000)
    }
}