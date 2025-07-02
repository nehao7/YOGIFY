package com.pardeep.yogify.presentation.login_signup_screen

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pardeep.yogify.R
import com.pardeep.yogify.presentation.setupScreens.SetupActivity

class LoginSignupActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ---------------------------- Day night mode ------------
        sharedPreferences = getSharedPreferences("DayNightMode" , MODE_PRIVATE)
        editor = sharedPreferences.edit()

        if(sharedPreferences.getBoolean("Night" , false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        // ---------------------------- Day night mode ------------

    }

    fun moveToSecondFragment() {
        val intent = Intent(this , SetupActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun loginSuccessfully() {
        startActivity(Intent(this , SetupActivity::class.java))
    }



}