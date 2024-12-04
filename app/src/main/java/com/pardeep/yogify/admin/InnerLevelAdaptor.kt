package com.pardeep.yogify.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pardeep.yogify.R

class InnerLevelAdaptor(
    var itemArray: ArrayList<InnerDayItemData>, val recyclerInterface: RecyclerInterface
) : RecyclerView.Adapter<InnerLevelAdaptor.ViewHolder>() {
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.InnerCardView)
        val name: TextView = view.findViewById(R.id.poseName)
        val level: TextView = view.findViewById(R.id.levelName)
        val duration: TextView = view.findViewById(R.id.duration)
        val imageView: ImageView = view.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.inner_level_fragment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(itemArray[position].packageName)
        holder.level.setText(itemArray[position].title)
        holder.duration.setText(itemArray[position].duration.toString())
        holder.imageView.setImageResource(itemArray[position].image)

        holder.cardView.setOnClickListener {
            recyclerInterface.onItemClick(position, "InnerLevel")

        }
    }

}
