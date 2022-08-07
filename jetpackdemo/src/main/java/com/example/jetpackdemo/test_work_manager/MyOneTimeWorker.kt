package com.example.jetpackdemo.test_work_manager

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.work.*
import java.util.concurrent.TimeUnit
import kotlin.contracts.contract


class MyOneTimeWorker(val context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {
    override fun doWork(): Result {
        Log.i("TestWorker ${Thread.currentThread().name}", "doWork")
        Thread.sleep(5000)
        Log.i("TestWorker ${Thread.currentThread().name}", "finish")
        val inputNumber = inputData.getLong("number", -1)
        val outPutData = Data.Builder().putLong("number_out", inputNumber + 1).build()
        context.getSharedPreferences("text", MODE_PRIVATE).edit().putLong("number $inputNumber",inputNumber).commit()
        return if ((inputNumber + 1) % 2 == 0L) {
            Result.success(outPutData)
        } else {
            Result.failure(outPutData)
        }
    }
}