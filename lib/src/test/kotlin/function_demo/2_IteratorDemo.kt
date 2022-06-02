package function_demo

import org.junit.Test

class `2_IteratorDemo` {

    class MyIterator(val data: List<String>) : Iterator<String> {
        var index = 0
        override fun hasNext(): Boolean = index < data.size

        override fun next(): String {
            if (hasNext()) {
                return data[index++]
            }
            throw  NoSuchElementException("only " + data.size + " elements");
        }
    }

    @Test
    fun test() {
        val iterator = MyIterator(listOf("java", "C", "C++"))
        for (e in iterator){
            println(e)
        }
    }

}