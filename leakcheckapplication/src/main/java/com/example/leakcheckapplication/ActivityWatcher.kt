package com.example.leakcheckapplication

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log

class ActivityWatcher(
    private val application: Application,
    private val objectWatcher: ObjectWatcher
) {
    fun install() {
        Log.i("ActivityWatcher", "install")
        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.i("ActivityWatcher","onActivityDestroyed")
                objectWatcher.watch(activity)
            }

        })
    }
}