package com.example.jetpackdemo.base_use

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.ActivityJetpackBaseUseBinding

class JetpackBaseUseActivity : AppCompatActivity() {
    private val rootViewBinding by lazy {
        ActivityJetpackBaseUseBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<JetPackBaseUseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootViewBinding.root)


        rootViewBinding.rotate1.setOnClickListener {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        }

        rootViewBinding.rotate2.setOnClickListener {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }

        rootViewBinding.clickBtn.setOnClickListener {
            viewModel.numberPlus()
        }
        viewModel.number
        viewModel.number.observe(this) {
            it ?: return@observe
            rootViewBinding.showNumberTv.text = it.toString()
        }

        val dm = getResources().getDisplayMetrics();

        val density = dm.density       // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        val densityDPI = dm.densityDpi        // 屏幕密度（每寸像素：120/160/240/320）



        Log.i("JetpackBaseUseActivity","$density $densityDPI")
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return super.onRetainCustomNonConfigurationInstance()
    }


    override fun getLastNonConfigurationInstance(): Any? {
        return super.getLastNonConfigurationInstance()
    }
}