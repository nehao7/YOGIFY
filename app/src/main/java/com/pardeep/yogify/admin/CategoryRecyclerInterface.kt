package com.pardeep.yogify.admin

interface CategoryRecyclerInterface {
    fun onItemClick(position : Int , callFrom : String)
    fun longClickListener(position: Int,callFrom: String )
}