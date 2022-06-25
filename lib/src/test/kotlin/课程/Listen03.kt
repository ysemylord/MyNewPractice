package 课程

import org.junit.Test

class Listen03 {
    @Test
    fun `基本类型的数组`() {
        val intArray1 = intArrayOf(1, 2, 3)
        val intArray2 = IntArray(5)
        val intArray3 = IntArray(5) { it + 2 }
        val floatArray1 = floatArrayOf(1f, 2f)
        val floatArray2 = FloatArray(5) { it + 2f }
    }

    class Student()

    @Test
    fun `类类型的数组`() {
        arrayOf<Int>(1, 2, 3)//Integer类型的数组
        arrayOf<String>("1", "2", "3")
        arrayOf<Student>(Student(), Student())
        arrayOfNulls<String>(5)
    }

    //函数的引用

    fun foo() {

    }

    fun foo(type: Int): Any {
        return Any()
    }

    class Foo {
        fun bar(name: String) {}
    }

    @Test
    fun `函数的引用`() {
        val foo1: () -> Unit = ::foo
        val foo2: (Int) -> Any = ::foo
        val bar1: Foo.(String) -> Unit = Foo::bar
        val foo = Foo()
        val bar2: (String) -> Unit = foo::bar
    }

    //变长参数
    fun mu1(vararg ages: Int) {
        println(ages)
    }

    fun mu2(vararg students: Student) {
        println(students)
    }

    @Test
    fun `变长参数`() {
        mu1(22, 32, 21)
        mu2(Student(), Student())
    }

    //多返回值
    private fun multiReturnValues(): Triple<Int, String, String> {
        return Triple(23, "男", "ping pang")
    }

    @Test
    fun `多返回值`() {
        val (age, sex, hobby) = multiReturnValues()
        println("年龄 $age , 性别 $sex , 爱好 $hobby")

    }

}