package com.serializable

import java.io.*


class TestSeriable {
    class Student(var name: String = "lord", var age: Int, var birth: Int = 900) : Serializable {
        companion object {
            const val serialVersionUID = -8812193626178821128L
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //inAndOut()
            writeToFile()
        }

        /**
         * 序列化对象写入文件
         */
        private fun writeToFile() {
       /*     val course = Student(100)
            val fileOutputStream = FileOutputStream("course.txt")
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(course)
            objectOutputStream.flush()
            objectOutputStream.close()*/

             ObjectInputStream(FileInputStream("course.txt")).run {
                 val course = readObject() as Student
                 println("${course.age}| ${course.name}  | ${course.birth}")
             }
        }

        /**
         * 写入字节
         */
        private fun inAndOut() {
         /*   val course = Student(23)
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(course)

            val courseBytes = byteArrayOutputStream.toByteArray()

            val byteArrayInputStream = ByteArrayInputStream(courseBytes)
            val objectInputStream = ObjectInputStream(byteArrayInputStream)
            val courseIn = objectInputStream.readObject() as Student

            print("age: ${courseIn.age}| name: ")*/
        }
    }
}