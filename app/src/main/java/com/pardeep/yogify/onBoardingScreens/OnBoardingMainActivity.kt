package com.pardeep.yogify.onBoardingScreens

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.denzcoskun.imageslider.adapters.ViewPagerAdapter
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.ActivityOnBoardingMainBinding

class OnBoardingMainActivity : AppCompatActivity() {
    var binding: ActivityOnBoardingMainBinding? = null
    val fragments = listOf(
        onBoardingScreen1(), onBoardingScreen2(), onBoardScreen3()
    )
    var viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, fragments)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_on_boarding_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ViewPager adapter linking
        binding?.viewPager?.adapter = viewPagerAdapter
        //binding?.viewPager?.registerOnPageChangeCallback(object :ViewPagerAdapter.onPageChangeCallback(binding!!.viewPager)

        //dot-Indicator functionality
        binding?.dotIndicator?.attachTo(binding!!.viewPager)



    }
}