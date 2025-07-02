package com.pardeep.yogify.presentation.mainScreen.homeScreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pardeep.yogify.R
import com.pardeep.yogify.thirdActivity.CategoryData
import com.pardeep.yogify.thirdActivity.CategoryRecyclerInterface

class CategoryAdaptor(
    var array: ArrayList<CategoryData>,
    var categoryRecyclerInterface: CategoryRecyclerInterface
):RecyclerView.Adapter<CategoryAdaptor.ViewHolder>() {
    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {
        val carditem = view.findViewById<CardView>(R.id.cardView)
        val title = view.findViewById<TextView>(R.id.tittle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_category_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.title.setText(array[position].name)
        holder.carditem.setOnClickListener{
            categoryRecyclerInterface.onItemClick(position , "categoryAdaptor")
        }

        holder.carditem.setOnLongClickListener {
            categoryRecyclerInterface.longClickListener(position,"categoryAdaptor" )
            return@setOnLongClickListener true
        }
    }

}
