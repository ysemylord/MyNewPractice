package com.example.otherapplication

import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val spannableString by lazy {

        val str =
            "Wow you’re a Top Fan! Your username is visible to everyone, but you can" +
                    "change your settings here."
        val spannableString = SpannableString(str)
        val spannableEnd = spannableString.length
        val spannableStart = spannableEnd - "change your settings here.".length
        spannableString.setSpan(
            ForegroundColorSpan(Color.RED),
            spannableStart,
            spannableEnd,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                Log.i("MyBaseDialogFragment", "click")
            }

        }, spannableStart, spannableEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableString
    }

    private val myBaseDialogFragment by lazy {
        MyBaseDialogFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.nine_png_test)
        setContentView(R.layout.activity_main)
        // val textView = findViewById<TextView>(R.id.nine_text_view)
        //textView.text = spannableString
    }

    fun showDialogFragment(view: View) {
        FragmentManager.enableDebugLogging(true)
        myBaseDialogFragment.show(supportFragmentManager, "MyBaseDialogFragment")
        myBaseDialogFragment.showPosition(0, 0, supportFragmentManager, "MyBaseDialogFragment")


    }

    fun showDialog(view: View) {
        Dialog(this).run {
            setContentView(R.layout.pop_dialog)
            window?.setGravity(Gravity.TOP or Gravity.START)
            window?.setBackgroundDrawableResource(android.R.color.holo_red_dark)
            show()
            Handler().postDelayed({
                this.window?.attributes?.run {
                    Log.i("showDialog", width.toString())
                    Log.i("showDialog", height.toString())
                    Log.i("showDialog", "--------")
                }
            }, 1000)
        }


    }
}