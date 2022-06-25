package base

fun <T:Any> returnAny(temp: () -> T): List<T> {
    return listOf(temp())
}

class 泛型 {
}