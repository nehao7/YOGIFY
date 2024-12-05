package com.pardeep.yogify.admin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pardeep.yogify.R
import com.pardeep.yogify.admin.ClickType
import com.pardeep.yogify.admin.clickInterface
import com.pardeep.yogify.admin.fragments.CategoriesListModel
import com.pardeep.yogify.databinding.CategoryListItemBinding


class CategoriesListAdapter(var context: Context,
                            var arrayList: ArrayList<CategoriesListModel>,
                            var clicklistener: clickInterface)
    :RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(var binding: CategoryListItemBinding):RecyclerView.ViewHolder(binding.root) {


    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding=CategoryListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            binding.tvcategory.setText(arrayList[position].categoryName)

            Glide
                .with(context)
                .load(arrayList[position].categoryImgUri)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.image)

            binding.ivAdd.setOnClickListener {
                clicklistener.onClick(position, ClickType.img)
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