package com.pardeep.yogify.login_signup_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentLoginSignupScreen2Binding
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 * Use the [login_signup_screen_2.newInstance] factory method to
 * create an instance of this fragment.
 */
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2: String = "param2"

class login_signup_screen_2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentLoginSignupScreen2Binding? = null
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginSignupScreen2Binding.inflate(layoutInflater)
        return binding?.root
        //return inflater.inflate(R.layout.fragment_login_signup_screen_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        binding?.signUp?.setOnClickListener {


            if (binding?.etEmail?.text?.trim().isNullOrBlank()) {
                binding?.emailTextInputLayout?.helperText = "required"
                binding?.confirmPasswordTextInputLayout?.setHelperTextColor(requireContext().getColorStateList(R.color.red))
            } else if (binding?.etPassword?.text?.trim().isNullOrBlank()) {
                binding?.passwordTextInputLayout?.helperText = "required"
                binding?.confirmPasswordTextInputLayout?.setHelperTextColor(requireContext().getColorStateList(R.color.red))

            } else if (binding?.etConfirmPassword?.text?.trim().isNullOrBlank()) {
                binding?.confirmPasswordTextInputLayout?.helperText = "required"
                binding?.confirmPasswordTextInputLayout?.setHelperTextColor(requireContext().getColorStateList(R.color.red))
            } else if (binding?.etPassword?.text.toString() != binding?.etConfirmPassword?.text.toString()) {
                binding?.confirmPasswordTextInputLayout?.helperText = "password does not match"
                binding?.confirmPasswordTextInputLayout?.setHelperTextColor(requireContext().getColorStateList(
                    R.color.red))
            } else {
                binding?.emailTextInputLayout?.helperText = ""
                binding?.passwordTextInputLayout?.helperText = ""
                binding?.confirmPasswordTextInputLayout?.helperText = ""
                var randomNumber = 0
                val min = 10000
                val max = 99999
                randomNumber = Random.nextInt(min, max + 1)
                println(randomNumber)
                val bundle = Bundle()
                bundle.putInt("randomNumber", randomNumber)
                navController = findNavController()
                navController.navigate(R.id.action_login_signup_screen_2_to_verification_Screen2, bundle)



                // navigate to next screen where you want
            }

        }


        //navigate to verification screen if user chooses to sign up with google or facebook or twitter
        binding?.googleBtn?.setOnClickListener {
         navController.navigate(R.id.action_login_signup_screen_2_to_verification_Screen2)
        }
        binding?.facebookBtn?.setOnClickListener {
            navController.navigate(R.id.action_login_signup_screen_2_to_verification_Screen2)
        }
        binding?.twitterBtn?.setOnClickListener {
            navController.navigate(R.id.action_login_signup_screen_2_to_verification_Screen2)
        }

        binding?.signInBtn?.setOnClickListener {
            navController.navigate(R.id.action_login_signup_screen_2_to_login_signup_screen_1)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment login_signup_screen_2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            login_signup_screen_2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}