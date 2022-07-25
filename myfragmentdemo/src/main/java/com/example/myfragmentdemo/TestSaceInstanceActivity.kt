package com.example.myfragmentdemo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment

class TestSaceInstanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_sace_instance)
    }

    class MyDialog: DialogFragment(){

        override fun onSaveInstanceState(outState: Bundle) {
           // dialog?.dismiss()
            super.onSaveInstanceState(outState)
        }
    }

    val fragment by lazy {
        MyDialog()
    }


    fun showDialog(view: View) {
        fragment.show(supportFragmentManager,"ttt")
    }

    fun switchFragment(view: View) {}
}