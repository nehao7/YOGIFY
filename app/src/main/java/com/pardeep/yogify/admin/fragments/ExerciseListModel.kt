package com.pardeep.yogify.admin.fragments

data class ExerciseListModel(
    var exrId:String?=null,
    var exrName:String?=null,
    var description:String?=null,
    var exrImgUri: String?=null,
    var duration: String?=null,
    var catId:String?=null,
    var level:Int?=0,
    var completed:Boolean=false,
    var beginnner:Boolean=false,//0
    var intermediate:Boolean=false,//1
    var advance:Boolean=false,//2

//    var subCategoryList: ArrayList<SubCategory>? = arrayListOf()
)