package com.pardeep.yogify.login_signup_screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import com.pardeep.yogify.Constants

import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentLoginSignupScreen1Binding

/**
 * A simple [Fragment] subclass.
 * Use the [login_screen.newInstance] factory method to
 * create an instance of this fragment.
 */

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class login_screen : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentLoginSignupScreen1Binding? = null
    lateinit var navController: NavController
    lateinit var loginSignupActivity: LoginSignupActivity
    private  val TAG = "login_signup_screen_1"
    var db = Firebase.firestore
    var progressBar: ProgressBar?=null

    var auth = Firebase.auth
    // ----------- firebase---------
    lateinit var firebaseAuth: FirebaseAuth
    // ----------- firebase---------


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
        binding = FragmentLoginSignupScreen1Binding.inflate(layoutInflater)
        progressBar = ProgressBar(requireContext(), null, android.R.attr.progressBarStyleSmall)
        progressBar=binding?.pbar
        return binding?.root
       // return inflater.inflate(R.layout.fragment_login_signup_screen_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        // ------------------------ fireAuth init----------------
        firebaseAuth = FirebaseAuth.getInstance()
        // ------------------------ fireAuth init----------------

        // ---------------- login functionality using email and password------------

        binding?.signIn?.setOnClickListener {

            if (binding?.etEmail?.text?.trim().isNullOrBlank()) {
                binding?.TextInputLayout?.helperText = "required*"
                binding?.TextInputLayout?.setHelperTextColor(requireContext().getColorStateList(R.color.red))
            } else if (binding?.etPassword?.text?.trim().isNullOrBlank()) {
                binding?.passwordTextInputLayout?.helperText = "required"
                binding?.passwordTextInputLayout?.setHelperTextColor(requireContext().getColorStateList(R.color.red))
            } else {
                db.collection(Constants.customers)
                    .whereEqualTo("useremail", binding!!.etEmail.text.toString())
                    .get().addOnSuccessListener { querySnapshot ->
                        Log.e("TAG", " querySnapshot ${querySnapshot.size()}")
                        if (querySnapshot.documents.isEmpty() || querySnapshot.size() == 0) {
                            Toast.makeText(
                                requireContext(),
                                "Customer is not registered, contact admin",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@addOnSuccessListener
                        }
                        binding?.pbar?.visibility = View.VISIBLE

                        Log.e("TAG", "querySnapshot.documents ${querySnapshot.documents}")
                        for (document in querySnapshot.documents) {
                            val userModel = document.toObject(CustomerRegModel::class.java)
                            userModel?.let {
                                auth.signInWithEmailAndPassword(
                                    binding?.etEmail?.text.toString(),
                                    binding?.etPassword?.text.toString()
                                ).addOnSuccessListener {
                                    Log.e("TAG", "in auth result ${userModel.type}")
                                    binding?.pbar?.visibility = View.GONE
                                    if (userModel.type == 1) {
                                        loginSignupActivity.moveToSecondFragment()
//
                                    } else {
                                        loginSignupActivity.moveToCustomerPanel()

                                        Log.e("TAG", "in else auth result ${userModel.type}")
//
                                    }
                                }
                                    .addOnFailureListener {
                                        binding?.pbar?.visibility = View.GONE
                                        Log.e(
                                            "failed",
                                            "onViewCreated: ${it.message.toString()} ${it.localizedMessage}"
                                        )
//                                            Toast.makeText(
//                                                this,
//                                                it.localizedMessage.toString(),
//                                                Toast.LENGTH_SHORT
//                                            ).show()
                                    }.addOnCompleteListener {
                                        if (it.isSuccessful == false) {
//                                                val errorCode =
//                                                    (it.exception as FirebaseAuthException).errorCode
//                                                Log.e(TAG, "error Code $errorCode")
                                        }
                                    }


                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        binding?.pbar?.visibility = View.GONE

                        Log.e("Firestore", "Error fetching document", e)
                        // Handle failure
                    }
                    .addOnCanceledListener {
                        binding?.pbar?.visibility = View.GONE

                    }
//                val email = binding?.etEmail?.text.toString()
//                val password = binding?.etPassword?.text.toString()
//                firebaseAuth.signInWithEmailAndPassword(email , password)
//                    .addOnCompleteListener {
//                        if (it.isSuccessful){
//                            Toast.makeText(requireContext(), "login successfully", Toast.LENGTH_SHORT).show()
//                            loginSignupActivity.moveToSecondFragment()
//
//                        }else{
//                            Toast.makeText(requireContext(), "Invalid Account", Toast.LENGTH_SHORT).show()
//                        }
//
//
//
//                }
            }

        }
        // ---------------- login functionality using email and password-------------

        //navigation to second screen for sign up when user don't have a account
       binding?.signUpBtn?.setOnClickListener {

           navController.navigate(R.id.action_login_signup_screen_1_to_login_signup_screen_2)
       }
        binding?.googleBtn?.setOnClickListener {
            navController.navigate(R.id.action_login_signup_screen_1_to_verification_Screen2)
        }
        binding?.facebookBtn?.setOnClickListener {
            navController.navigate(R.id.action_login_signup_screen_1_to_verification_Screen2)
        }
        binding?.twitterBtn?.setOnClickListener {
            navController.navigate(R.id.action_login_signup_screen_1_to_verification_Screen2)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment login_signup_screen_1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            login_screen().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}