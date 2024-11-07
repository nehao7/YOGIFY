package com.pardeep.yogify.thirdActivity

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.pardeep.yogify.R

class LevelAdaptor(
    val levelItem: List<String>,
    val imageItem: List<Int>
) : RecyclerView.Adapter<LevelAdaptor.ViewHolder>() {

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var image = view.findViewById<ImageView>(R.id.ImageView)
        var text = view.findViewById<TextView>(R.id.TextView)
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
        val actualPosition = position % levelItem.size
        holder.image.setImageResource(imageItem[actualPosition])
        holder.text.setText(levelItem[actualPosition])

    }

}
