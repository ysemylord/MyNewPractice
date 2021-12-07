package com.serializable

import java.io.*


class TestSeriableSomeMethod {
    class Student(var age: Int, var name: String = "lord", var birth: Int = 900) : Serializable{

        fun writeReplace():Any{
            return Student(100)
        }

        private fun writeObject(outputStream: ObjectOutputStream){
            outputStream.writeInt(age+1)
            outputStream.writeObject(name)
            outputStream.writeInt(birth)
        }

        private fun readObject(inputStream:ObjectInputStream){
            age = inputStream.readInt()-1
            name = inputStream.readObject() as String
            birth = inputStream.readInt()
        }

        fun readResolve():Any{
            return Student(1)
        }

    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            inAndOut()
        }


        /**
         * 写入字节流
         */
        private fun inAndOut() {
            val course = Student(23)
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(course)

            val courseBytes = byteArrayOutputStream.toByteArray()

            val byteArrayInputStream = ByteArrayInputStream(courseBytes)
            val objectInputStream = ObjectInputStream(byteArrayInputStream)
            val courseIn = objectInputStream.readObject() as Student

            print("age: ${courseIn.age} ")
        }
    }
}