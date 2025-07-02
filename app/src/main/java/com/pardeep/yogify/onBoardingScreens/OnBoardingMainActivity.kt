package com.pardeep.yogify.onBoardingScreens

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.ActivityOnBoardingMainBinding
import com.pardeep.yogify.presentation.login_signup_screen.LoginSignupActivity

class OnBoardingMainActivity : AppCompatActivity() {
    var binding: ActivityOnBoardingMainBinding? = null
    var currentPosition = 0
    val fragments = listOf(
        onBoardingScreen1(), onBoardingScreen2(), onBoardScreen3()
    )
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    var viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, fragments)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingMainBinding.inflate(layoutInflater)
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
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding?.imageBtn?.setImageResource(R.drawable.cloud_sun)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding?.imageBtn?.setImageResource(R.drawable.clouds_moon)

        }

        binding?.imageBtn?.setOnClickListener {
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
        // ---------------------------- Day night mode ------------


        //ViewPager adapter linking
        binding?.viewPager?.adapter = viewPagerAdapter
        binding?.viewPager?.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
         override fun onPageSelected(position: Int) {
             super.onPageSelected(position)
             currentPosition = position
         }
     })

        // next button navigation functionality for onboarding screen
        binding?.nextButton?.setOnClickListener {
            when(currentPosition){
                0 -> {
                    binding?.viewPager?.currentItem = 1
                }
                1 -> {
                    binding?.viewPager?.currentItem = 2
                }
                2 -> { val intent = Intent(this , LoginSignupActivity::class.java )
                    startActivity(intent)
                    finish()
                }

            }
        }

        //dot-Indicator functionality
        binding?.dotIndicator?.attachTo(binding!!.viewPager)



    }
    override fun onDestroy() { super.onDestroy() // Clean up binding
     binding?.viewPager?.adapter = null
}}