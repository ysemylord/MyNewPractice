package 课程.listen06

import java.io.File
import javax.xml.stream.events.Characters

class 统计字符个数 {
}

fun main() {
    File("build.gradle").readText().toCharArray()
        .filterNot(Character::isWhitespace)
    //.filterNot { Character.isWhitespace(it) }
    // { Character.isWhitespace(it) } -> {it-> Character.isWhitespace(it) }
    //->fun(Char):Boolean{Character.isWhitespace(it)}
        .groupBy {
            it
        }.map {
            it.key to it.value.size
        }.let {
            println(it)
        }

    Thread.sleep(100000)
}