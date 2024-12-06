package com.pardeep.yogify.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pardeep.yogify.DayDataClass
import com.pardeep.yogify.R

class CalenderAdapter(val days: List<DayDataClass>,
                      val recyclerInterface: RecyclerInterface) :RecyclerView.Adapter<CalenderAdapter.ViewHolder>(){
    class ViewHolder(var view : View): RecyclerView.ViewHolder(view)  {
        var day = view.findViewById<TextView>(R.id.day)
        var date = view.findViewById<TextView>(R.id.date)
        var cardView : CardView = view.findViewById(R.id.cardView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.custom_day_recycler_layout , parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return days.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day =  days[position]
        holder.date.setText(day.date)
        holder.day.setText(day.dayName)


        holder.cardView.setOnClickListener{
            recyclerInterface.onItemClick(position , "calenderAdaptor")
        }


    }

}
