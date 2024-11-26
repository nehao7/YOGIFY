package com.pardeep.yogify.thirdActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.pardeep.yogify.R

class MusicMeditationAdp() : RecyclerView.Adapter<MusicMeditationAdp.ViewHolder>() {

    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view){
        var image = view.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.category_specific_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageDrawable(R.drawable.ic_launcher_foreground.toDrawable())
    }

}
