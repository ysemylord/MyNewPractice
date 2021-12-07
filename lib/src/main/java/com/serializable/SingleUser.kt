package com.serializable

import java.io.*

class SingleUser : Serializable {


    companion object {

        private var instance: SingleUser? = null

        fun getInstance(): SingleUser? {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SingleUser()
                    }
                }
            }
            return instance
        }



        @JvmStatic
        fun main(args: Array<String>) {
            val user = SingleUser.getInstance()

            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)

            objectOutputStream.writeObject(user)
            val userBytes = byteArrayOutputStream.toByteArray()
            val objectInputStream = ObjectInputStream(ByteArrayInputStream(userBytes))
            val user1 = objectInputStream.readObject() as SingleUser?

            println("${user === user1}")

        }
    }

    private constructor()

    fun readResolve():Any?{
        return getInstance()
    }

}