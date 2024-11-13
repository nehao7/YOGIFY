package com.pardeep.yogify.thirdActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pardeep.yogify.R

class CategoryAdaptor(
    var array: ArrayList<CategoryData>,
    val recyclerInterface: RecyclerInterface
):RecyclerView.Adapter<CategoryAdaptor.ViewHolder>() {
    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {
        val carditem = view.findViewById<CardView>(R.id.cardView)
        val title = view.findViewById<TextView>(R.id.tittle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.custom_category_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.title.setText(array[position].title)
        holder.carditem.setOnClickListener{
            recyclerInterface.onItemClick(position , "categoryAdaptor")
        }
    }

}
