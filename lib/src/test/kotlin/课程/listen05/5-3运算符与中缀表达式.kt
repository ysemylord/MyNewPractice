package 课程.listen05

import org.junit.Test


class `5-3运算符与中缀表达式` {
    class Complex(private val real: Int = 0, private val image: Int = 0) {
        operator fun plus(complex: Complex): Complex =
            Complex(real + complex.real, image + complex.image)

        operator fun minus(complex: Complex): Complex =
            Complex(real - complex.real, image - complex.image)

        override fun toString(): String {
            return "$real + $image i"
        }
    }

    @Test
    fun testComplex() {
        val c1 = Complex(1, 2)
        val c2 = Complex(3, 4)
        println(c1 + c2)
        println(c1 - c2)

    }


    infix fun String.join(s: String): String {
        return "$this $s"
    }

    @Test
    fun testInfix1() {
        val s1 = "hell0".join("jack")
        val s2 = "hell0" join "jack"
    }

    @Test
    fun testInfix2() {
        val name = "name" to "jack"
        val map = mapOf<String, Int>(
            "jack" to 1,
            "lord" to 2
        )
    }

    //匿名函数和中缀表达式
    @Test
    fun testLambda() {

        val a = fun(n: Int): Int {
            return n + 1
        }
        println(a(1))

        val b = { n: Int ->
            n + 1
        }
        println(b(1))

        Thread.sleep(3000)
    }

    private class Man(val name:String,val age:Int){
        override fun equals(other: Any?): Boolean {
            return (other as? Man) ?.run {
                 return name==other.name && age==other.age
            }?:false
        }
    }
}