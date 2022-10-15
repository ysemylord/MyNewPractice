package com.example.customview

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.os.Build
import android.util.DisplayMetrics
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginStart
import kotlin.math.abs


class GestureActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture)
        val frontImageView = findViewById<ImageView>(R.id.front_image_View)

        val frontContainer = findViewById<View>(R.id.front_container)
        val rootContr = findViewById<ConstraintLayout>(R.id.root_contr)
        val anchorView = findViewById<View>(R.id.anchor_view)
        val barView = findViewById<View>(R.id.bar_view)
        var windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(dm)
        frontImageView.layoutParams.width = dm.widthPixels
        frontImageView.layoutParams.height = 800


        val gesture = GestureDetector(object : GestureDetector.OnGestureListener {

            var fingerX = 0

            override fun onDown(e: MotionEvent?): Boolean {
                Log.i("GestureDetector", "onDown")
                e?.run {
                    fingerX = this.x.toInt()
                }
                return false
            }

            override fun onShowPress(e: MotionEvent?) {

            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return true
            }

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {

                fingerX -= distanceX.toInt()
                Log.i("GestureDetector", "onScroll e1 ${e1.x}")
                Log.i("GestureDetector", "onScroll e2 ${e2.x}")
                Log.i("GestureDetector", "onScroll e2-e1 ${e2.rawX-e1.rawX}")
                anchorView.run {
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(rootContr)
                    constraintSet.setMargin(
                        R.id.anchor_view,
                        ConstraintSet.START,
                        (e2.rawX-e1.rawX).toInt()
                    )
                    constraintSet.applyTo(rootContr)
                }


                return true
            }

            override fun onLongPress(e: MotionEvent?) {

            }

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                return true
            }

        })
        anchorView?.setOnTouchListener { v, event -> gesture.onTouchEvent(event) }
        anchorView?.run {
            isClickable = true
            isLongClickable = true
        }
    }
}