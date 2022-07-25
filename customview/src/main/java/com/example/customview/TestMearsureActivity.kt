package com.example.customview

import android.content.Intent
import android.icu.util.Measure
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextPaint
import android.util.Log
import android.view.View
import android.widget.TextView

class TestMearsureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_mearsure)
       /* val textview = findViewById<TextView>(R.id.textview)
        textview.text="jacklordjackjacklordjackjacklordjackjacklordjackjacklordjackjacklordjackjacklordjackjacklordjackjacklordjackjacklordjack"
        textview.measure(
            View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST),
            View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST)
        )*/
        //Log.i("linecount","${textview.lineCount}")
        val textview = findViewById<TextView>(R.id.textview)
        textview.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}