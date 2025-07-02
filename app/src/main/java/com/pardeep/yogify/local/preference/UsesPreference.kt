package com.pardeep.yogify.local.preference

import android.content.Context

class UsesPreference(context: Context) {

    private val pref = context.getSharedPreferences("AppStatus",Context.MODE_PRIVATE)


    // auth login status
    fun userLoginStatus(status: Boolean){
        pref.edit().putBoolean("AuthStatus",status).apply()
    }

    // setup screen completion
    fun setupCompleteStatus(status: Boolean){
        pref.edit().putBoolean("SetupStatus",status).apply()
    }


}