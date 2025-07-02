package com.pardeep.yogify.presentation.setupScreens

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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.ActivitySetupBinding
import com.pardeep.yogify.local.preference.UsesPreference
import com.pardeep.yogify.presentation.viewModels.setupScreen.SetupActivityViewModel
import com.pardeep.yogify.presentation.viewModels.setupScreen.SetupViewModelFactory
import com.pardeep.yogify.presentation.mainScreen.ThirdActivity

class SetupActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var binding: ActivitySetupBinding
    lateinit var navController: NavController
    lateinit var viewModel: SetupActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val factory = SetupViewModelFactory(UsesPreference(this))
        viewModel = ViewModelProvider(this,factory)[SetupActivityViewModel::class.java]


        binding = DataBindingUtil.setContentView(this, R.layout.activity_setup)
        binding.lifecycleOwner = this
        binding.activityViewModel = viewModel

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
            viewModel.decrementProgressValue(newValue = 1)
            binding?.progressHorizontal?.progress = viewModel.progressBarValue.value!!
        }

        viewModel.progressBarValue.observe(this) { value ->
            binding?.progressHorizontal?.progress = value
        }

    }

    fun moveToSecondDestination() {
        val intent = Intent(this@SetupActivity, ThirdActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun progressBarIncrement(callFrom: String) {
        Toast.makeText(this@SetupActivity, "$callFrom", Toast.LENGTH_SHORT).show()
        when (callFrom) {
            "SetupScreen1" -> {
                viewModel.incrementProgressbarValue(newValue = 1)
            }

            "SetupScreen2" -> {
                viewModel.incrementProgressbarValue(newValue = 2)
            }

            "SetupScreen3" -> {
                viewModel.incrementProgressbarValue(newValue = 3)
            }

            "SetupScreen5" -> {
                viewModel.incrementProgressbarValue(newValue = 4)
            }

            "SetupScreen6" -> {
                viewModel.incrementProgressbarValue(newValue = 5)
            }

            "SetupScreen4" -> {
                viewModel.incrementProgressbarValue(newValue = 6)
                viewModel.updateSetupStatus(true)
            }

        }

    }


}