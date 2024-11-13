package com.pardeep.yogify.splashScreen

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pardeep.yogify.R
import com.pardeep.yogify.onBoardingScreens.OnBoardingMainActivity


class SplashScreen : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --------------------------- dayNightMode--------------------------
        sharedPreferences = getSharedPreferences("DayNightMode" , MODE_PRIVATE)
        editor = sharedPreferences.edit()

                            //-------------condition check--------------
        if (sharedPreferences.getBoolean("Night" , false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
                            //-------------condition check--------------


        // --------------------------- dayNightMode--------------------------



        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,OnBoardingMainActivity ::class.java))
        }, 3000)
    }
}