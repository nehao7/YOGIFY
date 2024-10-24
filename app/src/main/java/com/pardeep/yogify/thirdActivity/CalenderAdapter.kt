package com.pardeep.yogify.thirdActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.pardeep.yogify.R

class CalenderAdapter() :RecyclerView.Adapter<CalenderAdapter.ViewHolder>(){
    class ViewHolder(var view : View): RecyclerView.ViewHolder(view)  {
        var day = view.findViewById<TextView>(R.id.day)
        var date = view.findViewById<TextView>(R.id.date)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.custom_day_recycler_layout , parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 7
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

}
