package com.example.customview.backround_performance

import android.app.Activity
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.util.SparseIntArray
import android.widget.ImageView
import androidx.core.util.set
import java.util.LinkedList

/**
 *
 *
 * 思路
 *
 * 数据结构的选用
 *
 * 当缓存的Bitmap超过2个时，复用最久的未使用的bitmap
 *
 * 存储Bitmap使用LinkedList,
 * 因为这里打算实现一个简单的LRU,复用最久未是使用的Bitmap.
 * 最久未使用的bitmap放在链表首部，复用后将它放在链表尾部，涉及到频繁的删除插入。
 *
 * 使用一个map存储bitmap被那些界面使用
 * map选用的是ArrayMap。
 *
 *
 * 解决的问题
 * 1. 从其他界面返回时，要给当前重新设置界面的背景，因为bitmap被复用，导致bitmap的内容已经更改了
 *
 * 所以使用
 * map 记录界面和它的背景资源id
 *
 * BitmapFactory.Options inMutable 设置为true,返回的bitmap才能重用
 */

object BgController {

    private const val MAX = 2

    //这里为避免对Activity的引用可能造成的内存泄露，不直接存他们的对象
    private val bitmapToPage = ArrayMap<Bitmap, Int>()
    private val bitmapLinkedList = LinkedList<Bitmap>()
    private fun reuse(pageId: Int): Bitmap? {
        if (bitmapLinkedList.size < MAX) return null
        //根据LinkedHashMap的原理，按照访问排序时，链表第一个为最老的元素
        return bitmapLinkedList.removeFirst().apply {
            bitmapToPage[this] = pageId//覆盖掉之前的记录
        }
    }

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.i("LifecycleCallbacks", "${activity.hashCode()} onActivityCreated")
            }

            override fun onActivityStarted(activity: Activity) {
                Log.i("LifecycleCallbacks", "${activity.hashCode()} onActivityStarted")
            }

            override fun onActivityResumed(activity: Activity) {
                Log.i("LifecycleCallbacks", "${activity.hashCode()} onActivityResumed")
            }

            override fun onActivityPaused(activity: Activity) {
                Log.i("LifecycleCallbacks", "${activity.hashCode()} onActivityPaused")
            }

            override fun onActivityStopped(activity: Activity) {
                Log.i("LifecycleCallbacks", "${activity.hashCode()} onActivityStopped")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Log.i("LifecycleCallbacks", "${activity.hashCode()} onActivitySaveInstanceState")
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.i("LifecycleCallbacks", "${activity.hashCode()} onActivityDestroyed")
                val iterator = bitmapToPage.iterator()
                while (iterator.hasNext()) {
                    if (iterator.next().value == activity.hashCode()) {
                        iterator.remove()
                    }
                }
            }

        })
    }

    fun setBg(view: ImageView, activity: Activity, drawableId: Int) {

        val targetBitmap = BitmapFactory.decodeResource(
            view.resources,
            drawableId,
            BitmapFactory.Options().apply {
                inBitmap = reuse(activity.hashCode()).apply {
                    Log.i("BgController", "重用的 ${this?.hashCode()}")
                }
                inMutable = true//创建的bitmap是否可以重用，inMutable默认为false.
            })
        bitmapLinkedList.add(targetBitmap)
        view.setImageBitmap(targetBitmap)

    }

    fun onResume(view: ImageView, activity: Activity, drawableId: Int) {
        var haveSetBgBitmap = false
        bitmapToPage.forEach {
            if (it.value == activity.hashCode()) {
                haveSetBgBitmap = true
            }
        }
        if(!haveSetBgBitmap) {
            setBg(view, activity, drawableId)
        }
    }

}