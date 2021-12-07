package com.serializable

import java.io.*


/**
 * 同个对象多次写入，只会写入一次
 */
class WriteMoreTimeSeriable {
    class Student(var age: Int, var name: String = "lord", var birth: Int = 900) : Serializable

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            moreReferenceWrite()
        }

        //同个对象多次写入，只会写入一次
        private fun moreReferenceWrite() {
            val student = Student(23)
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(student)
            student.age = 24
            /* objectOutputStream.writeUnshared(course)*/
            objectOutputStream.reset()
            objectOutputStream.writeObject(student)
            // objectOutputStream.writeObject(student)
            val courseBytes = byteArrayOutputStream.toByteArray()
            val byteArrayInputStream = ByteArrayInputStream(courseBytes)
            val objectInputStream = ObjectInputStream(byteArrayInputStream)
            val student1 = objectInputStream.readObject() as Student
            val student2 = objectInputStream.readObject() as Student
            println("age: ${student1.age} | hashcode: ${student1.hashCode()}")
            println("age: ${student2.age} | hashcode: ${student2.hashCode()}")
            println("courseIn1===courseIn2 ${student1 === student2}")
        }



    }
}