package base_java

import org.junit.Test
import kotlin.concurrent.thread

class ThreadLocalDemo {

    @Test
    fun testOne(){
        val threadLocalName = ThreadLocal<String>()
        val thread1 = thread {
            threadLocalName.set("名字一")
            Thread.sleep(5000)
            println(threadLocalName.get())
        }

        val thread2 = thread {
            threadLocalName.set("名字2")
        }
        thread1.join()
    }
}