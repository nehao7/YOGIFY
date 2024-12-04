package com.pardeep.yogify.admin

import android.widget.ImageView

interface clickInterface {
    fun onClick(position: Int, clickType: ClickType ?= ClickType.Delete) :Boolean
    fun view(position: Int,imageView: ImageView)
}

enum class ClickType{
    Delete,img,OnViewClick,AddSub,Update
}