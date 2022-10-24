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

class BgContainerActivity : AppCompatActivity() {

    companion object {
        var index = 1
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bg_container)
        val bgId = resources.getIdentifier("bg_$index", "drawable", packageName)
        index++

        //理论上计算Bitmap的大小 800*1422*4byte/1024/1024 = 4.400m

        //通过BitmapFactory获取的bitmap的大小
        BitmapFactory.decodeResource(resources, R.drawable.bg_10).let {
            Log.i(
                "BgContainerActivity",
                "from BitmapFactory ${it.allocationByteCount / 1024f / 1024f}m"
            )
        }

        findViewById<ImageView>(R.id.image_view)?.let {
            it.setImageResource(bgId)
            it.post {
                //drawable中bitmap的大小 4.3395996m
                (it.drawable as? BitmapDrawable)?.bitmap?.let { bitmap ->
                    Log.i(
                        "BgContainerActivity",
                        "背景图片大小 ${bitmap.hashCode()} : ${bitmap.allocationByteCount / 1024f / 1024f}m"//4.3395996m
                    )
                }
            }
        }
    }



    fun startActivity(view: View) {
        startActivity(Intent(this, BgContainerActivity::class.java))
    }

    fun refresh(view: View) {
        findViewById<ImageView>(R.id.image_view)?.invalidate()
    }
}