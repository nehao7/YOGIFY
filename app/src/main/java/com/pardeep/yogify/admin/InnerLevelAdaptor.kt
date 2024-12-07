package com.pardeep.yogify.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.pardeep.yogify.R
import com.pardeep.yogify.admin.fragments.ExerciseListModel
import com.pardeep.yogify.databinding.InnerLevelFragmentBinding

class InnerLevelAdaptor(var context: Context,var arrayList: ArrayList<ExerciseListModel>, var clicklistener: clickInterface
) : RecyclerView.Adapter<InnerLevelAdaptor.ViewHolder>() {
    class ViewHolder(var binding: InnerLevelFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=
            InnerLevelFragmentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {holder.apply {
        Glide
            .with(context)
            .load(arrayList[position].exrImgUri)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imageView)

        binding.InnerCardView.setOnClickListener {
            clicklistener.onClick(position, ClickType.OnViewClick)
        }
        if (arrayList[position].level == 0){
            binding.levelName.setText("Beginner")
        }else if (arrayList[position].level == 1){
            binding.levelName.setText("Intermediate")
        }else if (arrayList[position].level == 2){
            binding.levelName.setText("Advance")
        }

        if (arrayList[position].completed == true ){
            binding.ivCompleted.visibility = View.VISIBLE
        }else{
            binding.ivCompleted.visibility = View.GONE
        }
        binding.duration.setText(arrayList[position].duration)
        binding.poseName.setText(arrayList[position].exrName)

    }
    }

}
