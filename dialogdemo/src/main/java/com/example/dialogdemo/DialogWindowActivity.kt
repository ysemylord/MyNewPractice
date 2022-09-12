package com.example.dialogdemo

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DialogWindowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_window)
    }

    fun showDialog(view: View) {
        Dialog(this).run{
            setContentView(R.layout.dialog_layout)
            show()
            val p = window!!.attributes
            Log.i("DialogWindowActivity","${p.width} -- ${p.height}")
            p.width = 300
            p.height = 300
            window?.attributes =p
            //Log.i("DialogWindowActivity","$width -- $height")
        }
    }
}