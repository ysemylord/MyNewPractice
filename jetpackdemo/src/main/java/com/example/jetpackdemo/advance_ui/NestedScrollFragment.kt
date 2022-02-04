package com.example.jetpackdemo.advance_ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.jetpackdemo.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NestedScrollFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NestedScrollFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nested_scroll, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val tab_layout = view.findViewById<TabLayout>(R.id.tab_layout)

        val view_pager = view.findViewById<ViewPager2>(R.id.view_pager).apply {
            adapter = HomeAdapterTest(activity ?: return)
        }

        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = "Tab ${(position + 1)}"
        }.attach()
    }

    class HomeAdapterTest(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun createFragment(position: Int): Fragment {
            return RecyclerViewFragment()
        }

        override fun getItemCount(): Int {
            return 6
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            NestedScrollFragment()
    }
}