package com.example.customview.MyLayoutInflaterFactory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.customview.R

class LayoutInflaterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        layoutInflater.factory2 = MyLayoutInflaterFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_inflater)
        val resName = resources.getResourceName(R.string.title)
        val packageName = resources.getResourcePackageName(R.string.title)
        val typeName = resources.getResourceTypeName(R.string.title)
        val entryName = resources.getResourceEntryName(R.string.title)
        Log.i("res info", "resName $resName ")
        Log.i("res info", "packageName $packageName")
        Log.i("res info", "typeName $typeName ")
        Log.i("res info", "entryName $entryName")

        val id = resources.getIdentifier(resName, typeName, packageName)
        Log.i("res info", "id is same :${id == R.string.title}")


    }
}