package com.pardeep.yogify.presentation.splashScreen

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.pardeep.yogify.R
import com.pardeep.yogify.onBoardingScreens.OnBoardingMainActivity
import com.pardeep.yogify.presentation.setupScreens.SetupActivity
import com.pardeep.yogify.presentation.mainScreen.ThirdActivity


class SplashScreen : AppCompatActivity() {

    //---------- firebase auth instance ---------
    val firebaseAuth = FirebaseAuth.getInstance()
    //---------- firebase auth instance ---------

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

        // provide share preference name and set it to private mode

        sharedPreferences = getSharedPreferences("AppStatus", MODE_PRIVATE)
        Handler(Looper.getMainLooper()).postDelayed({

            when {

                // If both false, go to OnBoarding
                !sharedPreferences.getBoolean("SetupStatus", false) && !sharedPreferences.getBoolean("AuthStatus", false) -> {
                    startActivity(Intent(this, OnBoardingMainActivity::class.java))
                }
                !sharedPreferences.getBoolean("SetupStatus", false) -> {
                    //if setupstatus is false then go to setup activity
                    startActivity(Intent(this, SetupActivity::class.java))
                }
                !sharedPreferences.getBoolean("AuthStatus", false) -> {
                    //if authStatus is false then go to auth activity

                    startActivity(Intent(this, ThirdActivity::class.java))
                }
                else -> {
                    // if both are true go to third activity
                    startActivity(Intent(this, ThirdActivity::class.java))
                }
            }

            finish() // ✅ Finish splash to prevent back navigation

        }, 2000)
    }
}