package com.example.jetpackdemo.advance_ui.dispatch_touch_event.innter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.jetpackdemo.R

/**
 * 演示使用内部解决法，解决滑动冲突
 */
class DispatchTouchEventInnerFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dispatch_touch_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ViewPager>(R.id.view_pager).run {
            adapter = SimpleViewPagerAdapter()
        }
    }

    private fun SimpleViewPagerAdapter() = object : PagerAdapter() {
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val listView = LayoutInflater.from(container.context)
                .inflate(R.layout.simple_inner_solution_list_view, container, false) as ListView
            container.addView(listView)
            listView.adapter = SimpleBaseAdapter()
            return listView
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

    private fun SimpleBaseAdapter() = object : BaseAdapter() {
        override fun getCount(): Int {
            return 100
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup?
        ): View {
            return LayoutInflater
                .from(parent?.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false).apply {
                    findViewById<TextView>(android.R.id.text1).text = "1222"
                }
        }

    }
}