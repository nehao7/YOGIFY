package com.pardeep.yogify.thirdActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pardeep.yogify.R
import org.w3c.dom.Text

class SpecificCategory(
    var array : ArrayList<SpecificData>,
    val recyclerInterface: RecyclerInterface
) : RecyclerView.Adapter<SpecificCategory.Viewholder>() {

    class Viewholder(var view : View) : RecyclerView.ViewHolder(view) {
        val cardView = view.findViewById<CardView>(R.id.cardView)
        val image = view.findViewById<ImageView>(R.id.imageView)
        val title = view.findViewById<TextView>(R.id.textView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.category_specific_view, parent, false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.image.setImageResource(array[position].image!!)
        holder.title.setText(array[position].title)

        holder.cardView.setOnClickListener{
            recyclerInterface.onItemClick(position , "specificAdaptor")
        }




    }

}
