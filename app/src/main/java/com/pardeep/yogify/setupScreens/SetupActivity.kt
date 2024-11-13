package com.pardeep.yogify.setupScreens

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.ActivitySetupBinding
import com.pardeep.yogify.thirdActivity.ThirdActivity

class SetupActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    var binding : ActivitySetupBinding?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySetupBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ---------------------------- Day night mode ------------
        sharedPreferences = getSharedPreferences("DayNightMode" , MODE_PRIVATE)
        editor = sharedPreferences.edit()

        if(sharedPreferences.getBoolean("Night" , false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        // ---------------------------- Day night mode ------------


        setSupportActionBar(binding?.tollbar)


    }

    fun moveToSecondDestination() {
        val intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
        finish()
    }



}