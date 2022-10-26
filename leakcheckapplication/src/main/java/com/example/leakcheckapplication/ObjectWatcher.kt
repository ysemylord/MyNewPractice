package com.example.leakcheckapplication

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.Executor

/**
 * 判断对象是否存活
 */
open class ObjectWatcher(
    private val executor: Executor = Executor {
        Handler(Looper.getMainLooper()).postDelayed(it, 5000)
    }
) {
    //存放标志为可回收的对象
    private val queue = ReferenceQueue<Any>()

    private val watchedObjects = mutableMapOf<String, KeyWeakReference<Any>>()

    fun watch(obj: Any) {
        val key = UUID.randomUUID()
            .toString()
        watchedObjects[key] = KeyWeakReference(key, obj, queue)
        executor.execute {
            //检查是否存活
            moveToRetain(key)
        }
    }

    fun moveToRetain(key: String) {
        System.gc()
        Thread.sleep(100)
        System.runFinalization()
        removeWeaklyReachableObjects()
        //watchedObjects是通过removeWeaklyReachableObjects清理一遍后的
        val ref = watchedObjects[key]
        ref?.run {
            //ref不为空，表示对象没有被回收
            Log.i("ObjectWatcher", "${this.get()?.javaClass?.simpleName} 可能没有被回收掉")
        }

    }

    //通过queue判断哪些对象已经被回收,将已回收的对象从watchedObjects中移除掉
    private fun removeWeaklyReachableObjects() {
        var weakReference: KeyWeakReference<Any>? = null
        do {
            weakReference = queue.poll() as KeyWeakReference<Any>?
            if (weakReference != null) {
                watchedObjects.remove(weakReference.key)
            }
        } while (weakReference != null)
    }
}

class KeyWeakReference<T>(val key: String, referent: T, q: ReferenceQueue<in T>) :
    WeakReference<T>(referent, q)