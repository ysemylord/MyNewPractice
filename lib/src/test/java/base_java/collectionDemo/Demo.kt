package base_java.collectionDemo

import org.junit.Test

class Demo {
    @Test
    fun one() {
        val linkedHashMap = LinkedHashMap<Int, String>(2, 0.4f, true)
        linkedHashMap[1] = "a"
        linkedHashMap[2] = "b"
        linkedHashMap[3] = "c"
        println(linkedHashMap[1])
        linkedHashMap.forEach {
            println(it.toString())
        }
        linkedHashMap.iterator().next().run {
            println(this.toString())
        }
    }

}