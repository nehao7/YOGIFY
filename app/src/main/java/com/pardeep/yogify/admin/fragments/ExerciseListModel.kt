package com.pardeep.yogify.admin.fragments

data class ExerciseListModel(
    var exrId:String?=null,
    var exrName:String?=null,
    var description:String?=null,
    var exrImgUri: String?=null,
    var duration: String?=null,
    var catId:String?=null,
    var beginnner:Boolean=false,
    var intermediate:Boolean=false,
    var difficult:Boolean=false,

//    var subCategoryList: ArrayList<SubCategory>? = arrayListOf()
)