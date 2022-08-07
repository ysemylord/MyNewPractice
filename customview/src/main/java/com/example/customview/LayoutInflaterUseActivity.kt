package com.example.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout

class LayoutInflaterUseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_inflater_use)
        layoutInflater.inflate(R.layout.layout_tmp_0, null, false).run {
            Log.i("LayoutInflaterUseActivity0", layoutParams?.toString() ?: "no param layout")
            val group = LinearLayout(this@LayoutInflaterUseActivity)
            group.addView(
                this,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }
        layoutInflater.inflate(R.layout.layout_tmp_0, LinearLayout(this), false).run {
            Log.i("LayoutInflaterUseActivity0", layoutParams?.toString() ?: "")
        }
    }
}