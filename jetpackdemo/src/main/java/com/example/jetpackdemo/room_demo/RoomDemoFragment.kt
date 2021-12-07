package com.example.jetpackdemo.room_demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jetpackdemo.R



class RoomDemoFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_room_demo, container, false)
        inflate.findViewById<View>(R.id.to_db).setOnClickListener {

        }
        return inflate
    }

    companion object {
        fun newInstance() =
            RoomDemoFragment()
    }
}