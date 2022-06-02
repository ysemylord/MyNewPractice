package function_demo

import org.junit.Test

/**
 * 函数带有Receiver后，函数内部就持有了Receiver,
 * this指向Receiver
 */
class `1_ReceiverFunction` {

    /**
     * 基础用例
     */
    @Test
    fun testBaseUse() {
        //声明一个带有receiver的函数(使用lambda表达式)
        //在函数题内部可以使用receiver的函数
        val join: String.(String) -> String = { other -> "$this $other" }
        //调用带有receiver的函数
        val result = "jack".join("lord")
        println(result)

        //声明一个带有receiver的函数(使用匿名函数)
        val sum2 = fun Int.(other: Int): Int {
            return this.plus(other)
        }
        println(sum2(2, 2))
    }

    /**
     * 显示有一个Teacher类
     * 它有一个giveClass（）方法，用于上课，
     * 上完课后还可以干其他的事，所以传递了一个() -> Unit类型的函数进去
     * 如giveClass1，在调用时，() -> Unit 要显示地调用teacher这个对象，来调用teacher的相关方法
     * 给（）->Unit 加上Receiver后，Teacher,() -> Unit,可以直接调用receiver的相关方法
     */

    /**
     * 从程序的设计上来讲，Receiver的用处
     * 我们的Teacher类的giveClass1的参数是一个函数，
     * 我们希望这个函数的作用域是是调用这个函数的Teacher实力，所以在这个函数上加一个Receiver
     */
    @Test
    fun testDemo() {
        val teacher = Teacher()

        teacher.giveClass1 {
            teacher.drinkTea()
            teacher.correctHomework()
        }

        Teacher().giveClass2 {
            drinkTea()
            correctHomework()
        }
        Thread.sleep(4000)
    }
    class Teacher {

        /**
         * 上课
         */
        fun giveClass1(afterGiveClass:() -> Unit) {
            println("上课")
            afterGiveClass()
        }

        fun giveClass2(afterGiveClass: Teacher.() -> Unit) {
            println("上课")
            afterGiveClass()
        }

        fun drinkTea() {
            println("喝茶")
        }


        fun correctHomework() {
            println("批改作业")
        }

    }


}