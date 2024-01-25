package com.example.customview.view_life_cycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.customview.R

class ViewLifecycleDemoActivity : AppCompatActivity() {

    val fragment =  LifecycleTestFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_lifecycle_demo)
        findViewById<View>(R.id.add_fragment).setOnClickListener {
            supportFragmentManager.beginTransaction().add(R.id.container,
                fragment,"fragment").commit()
        }

        findViewById<View>(R.id.remove_fragment).setOnClickListener {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }

    }
}