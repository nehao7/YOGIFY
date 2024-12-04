package com.pardeep.yogify.admin

import com.google.firebase.database.Exclude

data class CategoryData(
    var id : String?="",
    var name : String?="",
){
    @Exclude
    fun toMap() : Map<String , Any?>{
        return mapOf(
            "id" to id,
            "name" to name
        )
    }
}