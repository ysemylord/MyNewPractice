package com.example.jetpackdemo.advance_ui.recyclerview_top

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackdemo.R

data class StarData(var name: String, var groupName: String)

class StarRecyclerViewAdapter(private val data: List<StarData>) :
    RecyclerView.Adapter<StarRecyclerViewAdapter.MyViewHolder>() {

    fun isGroupHeader(position: Int): Boolean {
        return when (position) {
            0 -> true
            else -> {
                val nowGroupName = data[position].groupName
                val lastGroupName = data[position - 1].groupName
                return nowGroupName != lastGroupName
            }
        }
    }

    fun getGroupName(position: Int): String {
        return data[position].groupName
    }
    fun getItem(position: Int): StarData
    {
        return data[position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.star_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        (holder.itemView as TextView).text = "${data[position].groupName} : ${data[position].name}"
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}