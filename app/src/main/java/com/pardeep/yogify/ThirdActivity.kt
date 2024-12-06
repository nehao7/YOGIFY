package com.pardeep.yogify

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.pardeep.yogify.databinding.ActivityThirdBinding
import com.pardeep.yogify.login_signup_screen.LoginSignupActivity
import nl.joery.animatedbottombar.AnimatedBottomBar

class ThirdActivity : AppCompatActivity()  {
    var binding: ActivityThirdBinding? = null
    lateinit var navController: NavController
    private val TAG = "ThirdActivity"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var mainmenu: Unit
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mAuth: FirebaseAuth


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
        navController=findNavController(R.id.fragmentContainerView)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.lightGrey)))

        mAuth = Firebase.auth




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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        mainmenu=menuInflater.inflate(R.menu.menu_main, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.Category->{
                navController.navigate(R.id.categoriesFragment)
                return true
            }
            R.id.logout->{
                logout()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun logout() {
        mAuth.signOut()
        // Redirect to LoginActivity
        startActivity(Intent(this, LoginSignupActivity::class.java))
        finish() // Close MainActivity
        // Clear any saved user authentication state


    }
    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.popBackStack()
    }
}