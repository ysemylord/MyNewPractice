package kgeneric

import org.junit.Test
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible


annotation class Cache(val namespace: String)

@Cache(namespace = "globale")
class User(@property:Cache(namespace = "in user ") var age: Int, val id: String) {
    fun plushAge(num: Int): Int {
        this.age += num
        return age
    }
}

class Demo {

    @Test
    fun one() {
        val user = User(19, "id1")
        val kClass = User::class

        //注解
        kClass.annotations.find {
            it is Cache
        }?.let {
            it as Cache
        }?.let {
            println("User类上的注解为 ${it.namespace}")
        }


        //反射

        //properties
        kClass.declaredMemberProperties.forEach { property ->
            when (property.name) {
                "age" -> {
                    (property as KMutableProperty<*>).setter.call(user, 12)
                    println("user's new age ${user.age}")
                    (property.annotations.find { it is Cache } as Cache?)?.let {
                        println("注解的名字 ${it.namespace}")
                    }
                }
                "id" -> {
                    val name = property.getter.call(user)
                    println("user's id is $name")
                }
            }

            property.annotations
        }

        //function
        kClass.declaredFunctions.forEach { function ->
            when (function.name) {
                "plushAge" -> {
                    val res = function.call(user, 2)
                    println("调用plushAge后的结果 $res")
                }
            }
        }


    }

}