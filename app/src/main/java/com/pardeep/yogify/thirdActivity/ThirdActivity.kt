package com.pardeep.yogify.thirdActivity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.ActivityThirdBinding
import nl.joery.animatedbottombar.AnimatedBottomBar

class ThirdActivity : AppCompatActivity()  {
    var binding: ActivityThirdBinding? = null
    lateinit var navController: NavController
    private val TAG = "ThirdActivity"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        binding?.fragmentName?.setText("Exercise")


//        // ---------------------------- Day night mode ------------
        sharedPreferences = getSharedPreferences("DayNightMode", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        if (sharedPreferences.getBoolean("Night", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding?.dayNightModeBtn?.setImageResource(R.drawable.cloud_sun)
            binding?.drawerBtn?.setImageResource(R.drawable.bars_sort_night)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding?.dayNightModeBtn?.setImageResource(R.drawable.clouds_moon)
            binding?.drawerBtn?.setImageResource(R.drawable.bars_sort)
        }
        binding?.dayNightModeBtn?.setOnClickListener {
            changeTheme()
        }





        navController = findNavController(R.id.fragmentContainerView)

        binding?.bottomNavigation?.setOnTabSelectListener(object :
            AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when (newIndex) {
                    0 -> {
                        navController.navigate(R.id.exerciseFragment)
                        binding?.fragmentName?.setText("Exercise")
                    }

                    1 -> {
                        navController.navigate(R.id.trackingFragment)
                        binding?.fragmentName?.setText("Progress")
                    }

                    2 -> {
                        navController.navigate(R.id.profileFragment)
                        binding?.fragmentName?.setText("Profile")
                    }
                }
            }

            // An optional method that will be fired whenever an already selected tab has been selected again.
            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {

                Log.d(TAG, "onTabSelected:$index, title: ${tab.title}\"")

            }
        })
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.exerciseFragment -> binding?.bottomNavigation?.selectTabAt(0)
                R.id.trackingFragment -> binding?.bottomNavigation?.selectTabAt(1)
                R.id.profileFragment -> binding?.bottomNavigation?.selectTabAt(2)
                // ... add more cases for other fragments
            }
        }

    }

    private fun changeTheme() {
    // ---------------------------- Day night mode ------------
        binding?.dayNightModeBtn?.setOnClickListener {
            if (sharedPreferences.getBoolean("Night" , false)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("Night" , false)
                editor.commit()
                editor.apply()
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("Night" , true)
                editor.commit()
                editor.apply()
            }
        }
        // ---------------------------- Day night mode ------------
    }

    fun setName(name : String) {
        binding?.fragmentName?.setText(name)
    }


}