package com.example.jetpackdemo.advance_ui.recyclerview_top

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackdemo.R


/**
 * A simple [Fragment] subclass.
 * Use the [FixedTopRecyclerViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FixedTopRecyclerViewFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fixed_top_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.recycler_view).run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(StarItemDecoration())
            val starData = mutableListOf<StarData>().apply {
                for (i in 0..4) {
                    for (j in 0..10) {
                        add(
                            if (i % 2 == 0) {
                                (StarData("$j", "he-$i"))
                            } else {
                                (StarData("$j", "wang-$i"))
                            }
                        )
                    }
                }
            }
            adapter = StarRecyclerViewAdapter(starData)
        }
    }


}