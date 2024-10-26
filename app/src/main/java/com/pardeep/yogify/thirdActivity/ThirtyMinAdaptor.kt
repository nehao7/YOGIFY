package com.pardeep.yogify.thirdActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pardeep.yogify.R

class ThirtyMinAdaptor() : RecyclerView.Adapter<ThirtyMinAdaptor.Viewholder>() {
    class Viewholder(var view : View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_custom_layout, parent, false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

    }

}
