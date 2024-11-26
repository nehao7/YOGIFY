package com.pardeep.yogify.login_signup_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentLoginSignupScreen2Binding
import kotlin.math.log
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
    lateinit var loginSignupActivity: LoginSignupActivity
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginSignupActivity = activity as LoginSignupActivity
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

        //------------------- init fireAuth ---------------
        firebaseAuth = FirebaseAuth.getInstance()
        //------------------- init fireAuth ---------------

        binding?.signUp?.setOnClickListener {


            if (binding?.userNameEt?.text?.trim().isNullOrBlank()) {
                binding?.usernameTextInputLayout?.helperText = "required"
                binding?.usernameTextInputLayout?.setHelperTextColor(requireContext().getColorStateList(R.color.red))
            } else if (binding?.emailEt?.text?.trim().isNullOrBlank()) {
                binding?.emailTextInput?.helperText = "required"
                binding?.emailTextInput?.setHelperTextColor(requireContext().getColorStateList(R.color.red))

            } else if (binding?.passwordEt?.text?.trim().isNullOrBlank()) {
                binding?.passwordTextInputLayout?.helperText = "required"
                binding?.passwordTextInputLayout?.setHelperTextColor(requireContext().getColorStateList(R.color.red))
            }else {
                binding?.emailTextInput?.helperText = ""
                binding?.passwordTextInputLayout?.helperText = ""
                binding?.usernameTextInputLayout?.helperText = ""
                val email = binding?.emailEt?.text.toString()
                val password = binding?.passwordEt?.text.toString()
                val userName = binding?.userNameEt?.text.toString()
                firebaseAuth.createUserWithEmailAndPassword(email ,password).addOnCompleteListener {
                    navController.navigate(R.id.action_login_signup_screen_2_to_login_signup_screen_1)
                }
                    .addOnFailureListener { err ->
                        Toast.makeText(requireContext(), "${err.message}", Toast.LENGTH_SHORT).show()

                    }
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