package com.example.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import org.w3c.dom.Text

class ViewPagerShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_show)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = object : PagerAdapter() {

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val view = LayoutInflater.from(container.context)
                    .inflate(R.layout.view_page_view, container, false)
                view.findViewById<TextView>(R.id.text_view).text = position.toString()
                container.addView(view)
                return view
            }

            override fun getCount(): Int {
                return 3
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view === `object`
            }

            override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
                container.removeView(obj as View)
            }
        }
    }
}