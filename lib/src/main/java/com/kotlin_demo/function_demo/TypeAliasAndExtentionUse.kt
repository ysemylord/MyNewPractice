package com.kotlin_demo.function_demo

//typealias和扩展函数的综合使用

data class Baby(var name: String)

//给小孩取名
fun giveBabyName1(boy: Boolean = true, decorateName: (Baby) -> Unit): Baby {
    val name = when (boy) {
        true -> "jack"
        else -> "mary"
    }
    val baby = Baby(name)
    decorateName(baby)
    return baby
}

//使用 typealias 优化
typealias DecorateName = (Baby) -> Unit

fun giveBabyName2(boy: Boolean = true, decorateName: DecorateName): Baby {
    val name = when (boy) {
        true -> "jack"
        else -> "mary"
    }
    val baby = Baby(name)
    decorateName(baby)
    return baby
}
//限制DecorateName的使用范围
typealias DecorateName2 = Baby.() -> Unit

fun giveBabyName3(boy: Boolean = true, decorateName: DecorateName2): Baby {
    val name = when (boy) {
        true -> "jack"
        else -> "mary"
    }
    val baby = Baby(name)
    decorateName(baby)
    return baby
}

fun main() {
    val bady = giveBabyName3 {
        this.name = "cute ${this.name}"
    }
    print(bady)
}