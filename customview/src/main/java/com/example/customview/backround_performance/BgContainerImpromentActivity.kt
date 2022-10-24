package com.example.customview.backround_performance

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import com.example.customview.R

class BgContainerImpromentActivity : AppCompatActivity() {

    companion object {
        var index = 1
    }

    private val _index by lazy {
        index++
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bg_container)
        val bgId = resources.getIdentifier("bg_$_index", "drawable", packageName)
        BgController.setBg(
            findViewById(R.id.image_view),
            activity = BgContainerActivity@ this,
            bgId
        )

    }

    override fun onResume() {
        super.onResume()
        val bgId = resources.getIdentifier("bg_$_index", "drawable", packageName)
        BgController.onResume(
            findViewById(R.id.image_view),
            activity = BgContainerActivity@ this,
            bgId
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        index--
    }

    fun startActivity(view: View) {
        startActivity(Intent(this, BgContainerImpromentActivity::class.java))
    }

    fun refresh(view: View) {
        findViewById<ImageView>(R.id.image_view)?.requestLayout()
    }
}