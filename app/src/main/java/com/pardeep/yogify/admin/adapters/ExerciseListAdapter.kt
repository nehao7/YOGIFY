package com.pardeep.yogify.admin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.o7services.mycolouringbook.ClickType
import com.o7services.mycolouringbook.R
import com.o7services.mycolouringbook.clickInterface
import com.o7services.mycolouringbook.databinding.DrawingListItemBinding
import com.o7services.mycolouringbook.mydrawing.models.DrawingListModel


class ExerciseListAdapter(var context: Context, var arrayList: ArrayList<DrawingListModel>, var clicklistener: clickInterface):RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>() {

    class ViewHolder(var binding: DrawingListItemBinding):RecyclerView.ViewHolder(binding.root) {


    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding=DrawingListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            binding.tvcategory.setText(arrayList[position].drawingName)

            Glide
                .with(context)
                .load(arrayList[position].drawingImgUri)
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