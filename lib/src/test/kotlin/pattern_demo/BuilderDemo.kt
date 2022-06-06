package pattern_demo
/**
 * 类型安全的构造器
 */
class Car(
    val model: String? ,
    val year: Int
){
    companion object {
        /**
         * 带接收者的函数类型,这意味着我们需要向函数传递一个Builder类型的实例
         */
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder{
        var model: String? = null
        var year: Int = -1

        fun build() = Car(model ,year)
    }
}
class BuilderDemo {
    val  car = Car.build {
        model = "名字"
        year = 2017
    }
}