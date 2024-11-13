package com.pardeep.yogify.thirdActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pardeep.yogify.R

class InnerLevelAdaptor(
    val recyclerInterface: RecyclerInterface
) : RecyclerView.Adapter<InnerLevelAdaptor.ViewHolder>() {
    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {
        val cardView : CardView = view.findViewById(R.id.InnerCardView)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inner_level_fragment ,parent , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
holder.cardView.setOnClickListener{
    recyclerInterface.onItemClick(position , "InnerLevel")

}    }

}
