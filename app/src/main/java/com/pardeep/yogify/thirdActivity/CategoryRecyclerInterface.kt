package com.pardeep.yogify.thirdActivity

interface CategoryRecyclerInterface {
    fun onItemClick(position : Int , callFrom : String)
    fun longClickListener(position: Int,callFrom: String)
}