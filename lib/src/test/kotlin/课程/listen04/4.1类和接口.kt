package 课程.listen04

import org.junit.Test

class `类和接口` {
    //类和接口

    class SimpleClass(val name: String) {


        //副构造器，必须直接或者间接调用主构造器，保证类的初始化只有一条路径
        constructor(name: String, age: Int) : this(name) {

        }

        constructor(name: String, age: Int, hobby: String) : this(name,age) {

        }

        init {
            println("init 代码块")
        }
    }

    @Test
    fun `构造器`() {

    }

    //property 包含 field get() set()
   private class Person(){
       private var name:String = ""
                   get() {
                       return field
                   }
                   set(value) {
                       field = value
                   }
   }

  //扩展

}