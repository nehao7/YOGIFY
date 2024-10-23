package com.pardeep.yogify.thirdActivity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    var binding: ActivityThirdBinding? = null
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_third)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        navController = findNavController(R.id.fragmentContainerView)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.Exercise -> binding?.bottomNavigationView?.menu?.get(0)?.setChecked(true)
                R.id.Tracking -> binding?.bottomNavigationView?.menu?.get(1)?.setChecked(true)
                R.id.Search -> binding?.bottomNavigationView?.menu?.get(2)?.setChecked(true)
                R.id.Profile -> binding?.bottomNavigationView?.menu?.get(3)?.setChecked(true)
            }
            return@addOnDestinationChangedListener
        }


        binding?.bottomNavigationView?.setOnItemSelectedListener {item ->
                when (item.itemId) {
                    R.id.Exercise -> navController.navigate(R.id.exerciseFragment)
                    R.id.Tracking -> navController.navigate(R.id.trackingFragment)
                    R.id.Search -> navController.navigate(R.id.seachFragment)
                    R.id.Profile -> navController.navigate(R.id.profileFragment)
                }
                return@setOnItemSelectedListener true
            }


        }
}