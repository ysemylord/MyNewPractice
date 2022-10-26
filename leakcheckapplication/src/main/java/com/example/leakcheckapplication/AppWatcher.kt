package com.example.leakcheckapplication

import android.app.Application


object AppWatcher {
    private val objectWatcher = ObjectWatcher()
    fun install(application: Application) {
        //AppWatcher里面有各种watcher
        //这里实现了ActivityWatcher
        ActivityWatcher(application, objectWatcher).run {
            install()
        }
    }
}