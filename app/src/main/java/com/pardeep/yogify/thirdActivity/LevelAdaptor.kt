package com.pardeep.yogify.thirdActivity

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.pardeep.yogify.R

class LevelAdaptor(
    var array: ArrayList<LevelData>,
    val recyclerInterface: RecyclerInterface
) : RecyclerView.Adapter<LevelAdaptor.ViewHolder>() {

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var image = view.findViewById<ImageView>(R.id.image)
        var text = view.findViewById<TextView>(R.id.textView)
        var cardItem = view.findViewById<CardView>(R.id.levelCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_custom_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actualPosition = position % array.size
        holder.text?.setText(array[actualPosition].title)
        holder.image?.setImageResource(array[actualPosition].image!!)

        holder.cardItem.setOnClickListener{
            recyclerInterface.onItemClick(position , "LevelAdaptor")
        }

    }

}
