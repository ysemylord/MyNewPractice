package base

import org.junit.Test
import kotlin.reflect.KProperty

/**
 * 参考 https://blog.csdn.net/shu_lance/article/details/108776779
 */
class 委托 {
    /**
     * 类委托
     * 自定义一个Set接口的实现类Myset,
     * MySet要实现的方法委托给传入的set对象
     */
    class MySet<E>(set: HashSet<E>) : Set<E> by set {

    }

    /**
     * 属性委托
     */
    class MyClass {
        var temp by Delegate()
    }

    class Delegate {
        var propValue: Any? = null

        operator fun getValue(myClass: MyClass, prop: KProperty<*>): Any? {
            return propValue
        }

        operator fun setValue(myClass: MyClass, prop: KProperty<*>, value: Any?) {
            propValue = value
        }
    }

    @Test
    fun testMyClass() {
        val myClass = MyClass()
        myClass.temp = "jack"
        println(myClass.temp)
    }

}