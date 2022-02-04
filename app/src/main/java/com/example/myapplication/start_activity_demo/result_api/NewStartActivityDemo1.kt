package com.example.myapplication.start_activity_demo.result_api

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import com.example.myapplication.R

/**
 * Activity Results API
 */
class NewStartActivityDemo1 : AppCompatActivity() {
    //使用自定义的Contract
    val launcher1 = registerForActivityResult(object : ActivityResultContract<String, String>() {
        override fun createIntent(context: Context, input: String): Intent {
            return Intent(context, NewStartActivityDemo2::class.java).apply {
                putExtra("name", input)
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String {
            return intent?.getStringExtra("name") ?: ""
        }

    }) {
        Log.i("NewStartActivityDemo1", "传回的数据 $it")
    }

    //使用预定义的Contract
    val launcher2 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                val name = activityResult.data?.getStringExtra("name")
                Log.i("NewStartActivityDemo1", "传回的数据 $name")
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_start_demo1)
    }

    fun toDemo2Activity1(view: android.view.View) {

        launcher1.launch("jack")
    }

    fun toDemo2Activity2(view: android.view.View) {

        launcher2.launch(
            Intent(
                this@NewStartActivityDemo1,
                NewStartActivityDemo2::class.java
            ).apply {
                putExtra("name", "jack")
            })
    }
}