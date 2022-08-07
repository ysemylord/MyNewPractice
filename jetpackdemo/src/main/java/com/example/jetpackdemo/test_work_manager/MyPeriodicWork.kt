package com.example.jetpackdemo.test_work_manager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyPeriodicWork(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        Log.i("MyPeriodicWork","周期性任务")
        return Result.success()
    }
}