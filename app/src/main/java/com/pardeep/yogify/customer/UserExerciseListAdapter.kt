package com.pardeep.yogify.customer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pardeep.yogify.R
import com.pardeep.yogify.admin.ClickType
import com.pardeep.yogify.admin.clickInterface
import com.pardeep.yogify.admin.fragments.ExerciseListModel
import com.pardeep.yogify.databinding.ExerciseListItemBinding
import com.pardeep.yogify.databinding.UserExerciseListItemBinding


class UserExerciseListAdapter(var context: Context, var arrayList: ArrayList<ExerciseListModel>, var clicklistener: clickInterface):RecyclerView.Adapter<UserExerciseListAdapter.ViewHolder>() {

    class ViewHolder(var binding: UserExerciseListItemBinding):RecyclerView.ViewHolder(binding.root) {


    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding=UserExerciseListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            binding.tvcategory.setText(arrayList[position].exrName)
            binding.ivCompleted.visibility = View.GONE
            Glide
                .with(context)
                .load(arrayList[position].exrImgUri)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.image)

            binding.layout.setOnClickListener {
                clicklistener.onClick(position, ClickType.OnViewClick)
            }
            binding.imgDelete.setOnClickListener {
                clicklistener.onClick(position, ClickType.Delete)
            }
            binding.tvUpdate.setOnClickListener {
                clicklistener.onClick(position, ClickType.Update)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

//    interface imageSetting {
//        fun setImage(position: Int,imageView: ImageView)
//    }
}