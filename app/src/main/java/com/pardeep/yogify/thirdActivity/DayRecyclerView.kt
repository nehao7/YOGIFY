package com.pardeep.yogify.thirdActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pardeep.yogify.R


class DayRecyclerView(var dayData : ArrayList<String>,
    val recyclerInterface: RecyclerInterface) :RecyclerView.Adapter<DayRecyclerView.ViewHolder>() {
    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {

        var cardView : CardView = view.findViewById(R.id.dayRecyclerCardView)
        var text = view.findViewById<TextView>(R.id.dateTv)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_recycler_view , parent , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dayData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardView.setOnClickListener{
            recyclerInterface.onItemClick(position, "DayRecycler")
        }

    }

}
