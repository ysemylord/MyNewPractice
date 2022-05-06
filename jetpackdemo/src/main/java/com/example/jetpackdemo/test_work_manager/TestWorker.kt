package com.example.jetpackdemo.test_work_manager

import android.content.Context
import android.util.Log
import androidx.work.*
import java.util.concurrent.TimeUnit

fun testWorkManager(context: Context){
    val sendLogRequest = OneTimeWorkRequest.Builder(TestWorker::class.java)
        .setInitialDelay(2, TimeUnit.SECONDS)
        .build()

    WorkManager.getInstance(context)
        .enqueueUniqueWork("liveStreamLog", ExistingWorkPolicy.KEEP, sendLogRequest)
}

class TestWorker(context: Context, parameters: WorkerParameters):Worker(context,parameters) {
    override fun doWork(): Result {
        Log.i("TestWorker","doWork")
        return Result.success()
    }
}