package com.pardeep.yogify.onBoardingScreens

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.denzcoskun.imageslider.adapters.ViewPagerAdapter
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.ActivityOnBoardingMainBinding
import com.pardeep.yogify.login_signup_screen.LoginSignupActivity

class OnBoardingMainActivity : AppCompatActivity() {
    var binding: ActivityOnBoardingMainBinding? = null
    var currePosition = 0
    val fragments = listOf(
        onBoardingScreen1(), onBoardingScreen2(), onBoardScreen3()
    )
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

        //ViewPager adapter linking
        binding?.viewPager?.adapter = viewPagerAdapter
        binding?.viewPager?.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
         override fun onPageSelected(position: Int) {
             super.onPageSelected(position)
             currePosition = position
         }
     })

        // next button navigation functionality for onboarding screen
        binding?.nextButton?.setOnClickListener {
            when(currePosition){
                0 -> {
                    binding?.viewPager?.currentItem = 1
                    binding?.cardView2?.visibility = View.VISIBLE
                }
                1 -> {
                    binding?.viewPager?.currentItem = 2
                    binding?.cardView2?.visibility = View.VISIBLE
                }
                2 -> {
                    binding?.cardView2?.visibility = View.VISIBLE
                    val intent = Intent(this ,LoginSignupActivity::class.java )
                    startActivity(intent)
                    finish()
                }

            }
        }

        // previous button navigation functionality for onboarding screen
        binding?.previousButton?.setOnClickListener {
            when(currePosition){
                2->{
                    binding?.viewPager?.currentItem = 1
                    binding?.cardView2?.visibility = View.VISIBLE
                }
                1->{
                    binding?.viewPager?.currentItem = 0
                    binding?.cardView2?.visibility = View.INVISIBLE
                }
            }
        }

        //dot-Indicator functionality
        binding?.dotIndicator?.attachTo(binding!!.viewPager)



    }
}