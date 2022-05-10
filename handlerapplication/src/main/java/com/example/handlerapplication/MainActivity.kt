package com.example.handlerapplication

import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testScissors()
    }
}

fun testScissors() {
    Thread1().start()
}

class Thread1 : Thread("t1") {
    override fun run() {
        super.run()
        Log.i(currentThread().name, "start")

        val thread2 = Thread2().apply { start() }
        val handler2 = MyHandler(thread2.looper)
        handler2.runMyWithScissors({
               Log.i(currentThread().name,"doing")
        },0)

        Log.i(currentThread().name, "end")
    }
}

class Thread2 : HandlerThread("t2") {


}

class MyHandler(looper: Looper):Handler(looper){
    fun runMyWithScissors(r: Runnable, timeout: Long): Boolean {
        requireNotNull(r) { "runnable must not be null" }
        require(timeout >= 0) { "timeout must be non-negative" }
        if (Looper.myLooper() == looper) {
            r.run()
            return true
        }
        val br = BlockingRunnable(r)
        return br.postAndWait(this, timeout)
    }

    private class BlockingRunnable(private val mTask: Runnable) : Object(),Runnable {
        private var mDone = false
        override fun run() {
            try {
                mTask.run()
            } finally {
                synchronized(this) {
                    mDone = true
                    notifyAll()
                }
            }
        }

        fun postAndWait(handler: Handler, timeout: Long): Boolean {
            if (!handler.post(this)) {
                return false
            }
            synchronized(this) {
                if (timeout > 0) {
                    val expirationTime = SystemClock.uptimeMillis() + timeout
                    while (!mDone) {
                        val delay = expirationTime - SystemClock.uptimeMillis()
                        if (delay <= 0) {
                            return false // timeout
                        }
                        try {
                            wait(delay)
                        } catch (ex: InterruptedException) {
                        }
                    }
                } else {
                    while (!mDone) {
                        try {
                            wait()
                        } catch (ex: InterruptedException) {
                        }
                    }
                }
            }
            return true
        }
    }
}
