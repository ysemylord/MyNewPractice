package com.example.myfragmentdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myfragmentdemo.parent_child.ViewPagerParentFragment

class ViewPager2MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2_main)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        viewPager.adapter =object: FragmentStateAdapter(this){
            override fun getItemCount(): Int {
               return 3
            }

            override fun createFragment(position: Int): Fragment {
                return  ViewPagerParentFragment()
            }

        }
    }
}