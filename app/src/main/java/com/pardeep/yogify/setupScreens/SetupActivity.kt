package com.pardeep.yogify.setupScreens

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.ActivitySetupBinding
import com.pardeep.yogify.thirdActivity.ThirdActivity

class SetupActivity : AppCompatActivity()  {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var binding: ActivitySetupBinding? = null
    lateinit var navController: NavController
    var currentProgress : Int = 0

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
        navController = findNavController(R.id.fragmentContainerView2)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val currentlabel = destination.label
            if (currentlabel == "fragment_setup_screen_1") {
                binding?.imageBtn?.visibility = View.INVISIBLE
                //Toast.makeText(this, "$currentFragment", Toast.LENGTH_SHORT).show()
            } else {
                binding?.imageBtn?.visibility = View.VISIBLE
            }
        }


        // ---------------------------- Day night mode ------------
        sharedPreferences = getSharedPreferences("DayNightMode", MODE_PRIVATE)
        editor = sharedPreferences.edit()
        if (sharedPreferences.getBoolean("Night", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding?.imageBtn2?.setImageResource(R.drawable.cloud_sun)
            binding?.imageBtn?.setImageResource(R.drawable.arrow_small_left_night)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding?.imageBtn2?.setImageResource(R.drawable.clouds_moon)
            binding?.imageBtn?.setImageResource(R.drawable.arrow_small_left)
        }
        // ---------------------------- Day night mode ------------


        binding?.imageBtn2?.setOnClickListener {
            if (sharedPreferences.getBoolean("Night", false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("Night", false)
                editor.commit()
                editor.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("Night", true)
                editor.commit()
                editor.apply()
            }
        }



        binding?.imageBtn?.setOnClickListener {
            navController.popBackStack()
            currentProgress = currentProgress -1
            binding?.progressHorizontal?.progress = currentProgress
        }


    }

     fun moveToSecondDestination() {
        val intent = Intent(this@SetupActivity, ThirdActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun progressBarIncrement(callFrom: String) {
        Toast.makeText(this@SetupActivity, "$callFrom", Toast.LENGTH_SHORT).show()
        when(callFrom){
            "SetupScreen1" ->{
                binding?.progressHorizontal?.progress = 1
                currentProgress = 1
            }
            "SetupScreen2" ->{
                binding?.progressHorizontal?.progress = 2
                currentProgress = 2
            }
            "SetupScreen3" ->{
                binding?.progressHorizontal?.progress = 3
                currentProgress = 3
            }
            "SetupScreen5" ->{
                binding?.progressHorizontal?.progress = 4
                currentProgress = 4
            }
            "SetupScreen6" ->{
                binding?.progressHorizontal?.progress = 5
                currentProgress = 5
            }
            "SetupScreen4" ->{
                binding?.progressHorizontal?.progress = 6
                currentProgress = 6
            }

        }

    }


}