package com.example.jetpackdemo.room_demo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jetpackdemo.MyApp
import com.example.jetpackdemo.R
import com.example.jetpackdemo.room_demo.database.AppDataBase
import com.example.jetpackdemo.room_demo.entity.StudentEntity
import kotlin.concurrent.thread
import kotlin.random.Random


class RoomDemoFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_room_demo, container, false)
        inflate.findViewById<View>(R.id.to_add_student).setOnClickListener {
            AppDataBase.getInstance(MyApp.instance.applicationContext)
                .run {
                    thread {
                        studentDao.insertStudent(
                            StudentEntity(
                                name = "jack ${Random.nextInt()}",
                                password = Random.nextInt().toString(),
                            )
                        )
                    }
                }
        }

        inflate.findViewById<View>(R.id.to_query_student).setOnClickListener {
            AppDataBase.getInstance(MyApp.instance.applicationContext)
                .run {
                    thread {
                        studentDao.queryAll().run {
                            Log.i("query", toString())
                        }
                    }
                }
        }

        return inflate
    }

    companion object {
        fun newInstance() =
            RoomDemoFragment()
    }
}