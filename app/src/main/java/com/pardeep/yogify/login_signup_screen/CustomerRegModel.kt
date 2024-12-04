package com.pardeep.yogify.login_signup_screen

data class CustomerRegModel(
    var customerId:String?="",
    var username:String?="",
    var useremail:String?="",
    var userpassword:String?="",
    var userauthId:String?="",
    var type : Int = 0
    )
{
    override fun toString(): String {
        return "$username"
    }
}
