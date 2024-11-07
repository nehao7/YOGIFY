package com.pardeep.yogify.login_signup_screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pardeep.yogify.setupScreens.ActivityDestination
import com.pardeep.yogify.R
import com.pardeep.yogify.setupScreens.SetupActivity

class LoginSignupActivity : AppCompatActivity() {
    var activityDestination : ActivityDestination?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    fun moveToSecondFragment() {
        val intent = Intent(this , SetupActivity::class.java)
        startActivity(intent)
        finish()
    }


}