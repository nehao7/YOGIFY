package com.pardeep.yogify.thirdActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.ActivityThirdBinding
import nl.joery.animatedbottombar.AnimatedBottomBar

class ThirdActivity : AppCompatActivity() {
    var binding: ActivityThirdBinding? = null
    lateinit var navController: NavController
    private  val TAG = "ThirdActivity"
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


        navController = findNavController(R.id.fragmentContainerView)


        binding?.bottomNavigation?.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when(newIndex){
                    0 ->{
                        navController.navigate(R.id.exerciseFragment)
                    }
                    1->{
                        navController.navigate(R.id.trackingFragment)
                    }
                    2->{
                        navController.navigate(R.id.seachFragment)
                    }
                    3->{
                        navController.navigate(R.id.profileFragment)
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
                R.id.seachFragment -> binding?.bottomNavigation?.selectTabAt(2)
                R.id.profileFragment -> binding?.bottomNavigation?.selectTabAt(3)
                // ... add more cases for other fragments
            }
        }

    }




}