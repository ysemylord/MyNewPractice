package com.example.customview.onclick_event_dispathcer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.customview.R

class OnclickEventDispatchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onclick_event_dispatch)
    }

    fun click(view: View) {
        /**
         * performClickInternal 是被post出去的，所以这个log会在最后打印。
         *  private final class PerformClick implements Runnable {
         *   @Override
         *   public void run() {
         *    recordGestureClassification(TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__SINGLE_TAP);
         *    performClickInternal();
         *   }
         *  }
         */

        Log.i("event track View", "onclick listener")
    }
}