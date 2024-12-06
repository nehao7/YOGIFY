package com.pardeep.yogify.admin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.pardeep.yogify.R
import com.pardeep.yogify.admin.ClickType
import com.pardeep.yogify.admin.clickInterface
import com.pardeep.yogify.admin.fragments.ExerciseListModel
import com.pardeep.yogify.databinding.ExerciseListItemBinding


class ExerciseListAdapter(var context: Context, var arrayList: ArrayList<ExerciseListModel>,val firebaseAuth: FirebaseAuth, var clicklistener: clickInterface,
    ):RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>() {

    class ViewHolder(var binding: ExerciseListItemBinding):RecyclerView.ViewHolder(binding.root) {


    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding=ExerciseListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            if (firebaseAuth.currentUser?.email.toString() == "admin@gmail.com"){
                binding.imgUpdate.visibility = View.VISIBLE
                binding.imgDelete.visibility = View.VISIBLE
            }else{
                binding.imgUpdate.visibility = View.GONE
                binding.imgDelete.visibility = View.GONE
            }
            binding.tvcategory.setText(arrayList[position].exrName)
            if (arrayList[position].completed){
                binding.ivCompleted.visibility=View.VISIBLE
            }else{
                binding.ivCompleted.visibility= View.INVISIBLE
            }

            Glide
                .with(context)
                .load(arrayList[position].exrImgUri)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.image)

            binding.cardView.setOnClickListener {
                clicklistener.onClick(position, ClickType.OnViewClick)
            }
            binding.imgDelete.setOnClickListener {
                clicklistener.onClick(position, ClickType.Delete)
            }
            binding.imgUpdate.setOnClickListener {
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