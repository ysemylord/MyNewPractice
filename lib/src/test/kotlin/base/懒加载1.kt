package base

import kotlin.random.Random
import kotlin.reflect.KProperty

/**
 * 参考 https://blog.csdn.net/shu_lance/article/details/108776779
 */
fun <T> later(block: () -> T) = MyLater(block)

class MyLater<T>(val block: () -> T) {
    var propValue: T? = null
    operator fun getValue(any: Any, prop: KProperty<*>): T? {
        return if (propValue == null) {
            println("value is not exist")
            propValue = block()
            propValue
        } else {
            println("value is exist")
            propValue
        }
    }
}

class TestLazy1 {
    val name by later {
        val random = Random(100).nextInt().toString()
        random + "jack"
    }
}

fun main() {
    val 懒加载 = TestLazy1()
    println(懒加载.name)
    println(懒加载.name)
    println(懒加载.name)
}
//输出结果
/**
 * value is not exist
  -234394110jack

   value is exist
   -234394110jack

   value is exist
   -234394110jack
 */
