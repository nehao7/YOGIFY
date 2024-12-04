package com.pardeep.yogify.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pardeep.yogify.R

class SpecificCategory(
    var array : ArrayList<SpecificData>,
    val recyclerInterface: RecyclerInterface
) : RecyclerView.Adapter<SpecificCategory.ViewHolder>() {

    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {
        val cardView = view.findViewById<CardView>(R.id.cardView)
        val image = view.findViewById<ImageView>(R.id.imageView)
        val title = view.findViewById<TextView>(R.id.textView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.category_specific_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(array[position].image!!)
        holder.title.setText(array[position].title)

        holder.cardView.setOnClickListener{
            recyclerInterface.onItemClick(position , "specificAdaptor")
        }




    }

}
