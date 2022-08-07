package com.example.jetpackdemo.test_work_manager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentWorkManagerDemoBinding
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class WorkManagerDemoFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflateView = FragmentWorkManagerDemoBinding.inflate(inflater, container, false)

        inflateView.oneTimeWork.setOnClickListener {

            val sendRequest = OneTimeWorkRequest.Builder(MyOneTimeWorker::class.java)
                .setInputData(Data.Builder().putLong("number", System.currentTimeMillis()).build())
                .build()

            WorkManager.getInstance(requireContext()).getWorkInfoByIdLiveData(sendRequest.id).observe(
                viewLifecycleOwner
            ) { t ->
                t?.let { workInfo ->
                    Log.i("WorkInfo", "$workInfo")

                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            workInfo.outputData.getInt("number_out", -1).let {
                                Log.i("WorkInfo", "work 执行成功 $it")
                            }
                        }
                        WorkInfo.State.FAILED -> {
                            workInfo.outputData.getInt("number_out", -1).let {
                                Log.i("WorkInfo", "work 失败 $it")
                            }
                        }
                        else -> {

                        }
                    }
                }
            }
            WorkManager.getInstance(requireContext())
                .enqueueUniqueWork("liveStreamLog", ExistingWorkPolicy.KEEP, sendRequest)//任务唯一，不会重复执行
                //.enqueue( sendRequest) //任务可以重复执行

        }

        inflateView.periodicWork.setOnClickListener {
            //周期性任务最小间隔为15分钟
            val sendRequest = PeriodicWorkRequest.Builder(MyPeriodicWork::class.java,15,TimeUnit.MINUTES)
                .build()
            WorkManager.getInstance(requireContext()).enqueue(sendRequest)
        }

        return inflateView.root
    }

    companion object {
        fun newInstance() =
            WorkManagerDemoFragment()
    }
}