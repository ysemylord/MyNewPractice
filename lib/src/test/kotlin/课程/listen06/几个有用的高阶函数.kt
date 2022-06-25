package 课程.listen06

class 几个有用的高阶函数 {
}

private data class Person(var name: String)

fun main() {
    Person("jack").let {
        "jack test"
    }.let {
        println(it)
    }

    Person("lord").also {
        "lord test"
    }.also {
        print(it)
    }
}