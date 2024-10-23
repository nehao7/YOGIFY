package com.pardeep.yogify.setupScreens

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pardeep.yogify.ActivityDestination
import com.pardeep.yogify.R
import com.pardeep.yogify.thirdActivity.ExerciseFragment
import com.pardeep.yogify.thirdActivity.ThirdActivity

class SetupActivity : AppCompatActivity() {
    var activityDestination :ActivityDestination?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun moveToSecondDestination() {
      val intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
        finish()
    }

}