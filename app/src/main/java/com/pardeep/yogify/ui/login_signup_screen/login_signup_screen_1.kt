package com.pardeep.yogify.ui.login_signup_screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentLoginSignupScreen1Binding
import com.pardeep.yogify.repositoryImplementation.AuthRepoUseCase
import com.pardeep.yogify.ui.viewModels.login.LoginViewModel
import com.pardeep.yogify.ui.viewModels.login.LoginViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [login_signup_screen_1.newInstance] factory method to
 * create an instance of this fragment.
 */

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class login_signup_screen_1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentLoginSignupScreen1Binding? = null
    lateinit var navController: NavController
    lateinit var loginSignupActivity: LoginSignupActivity
    private val TAG = "login_signup_screen_1"
    private lateinit var loginViewModel: LoginViewModel
    val GOOGLE_SIGN_IN_REQUEST_CODE = 121




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
        // get instance of auth repo
        val authRepo = AuthRepoUseCase(FirebaseAuth.getInstance())
        val factory = LoginViewModelFactory(authRepo)

        loginViewModel =
            ViewModelProvider(requireActivity(), factory)[LoginViewModel::class.java]
        // Data binding
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login_signup_screen_1,
            container,
            false
        )

        binding?.lifecycleOwner = this
        binding?.loginViewModel = loginViewModel
        // return inflater.inflate(R.layout.fragment_login_signup_screen_1, container, false)
        return binding?.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {

        super.onViewStateRestored(savedInstanceState)
        navController = findNavController()

        // for token using googleSignInOption
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOption)
        // for token using googleSignInOption


        // ---------------- login functionality using email and password------------
        binding?.signIn?.setOnClickListener {
            loginViewModel.loginWithEmailAndPassword()
        }
        // ---------------- login functionality using email and password-------------

        binding?.signUpBtn?.setOnClickListener {
            navController.navigate(R.id.login_signup_screen_2)
        }
        binding?.googleBtn?.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
        }
//        binding?.facebookBtn?.setOnClickListener {
//            navController.navigate(R.id.action_login_signup_screen_1_to_verification_Screen2)
//        }
//        binding?.twitterBtn?.setOnClickListener {
//            navController.navigate(R.id.action_login_signup_screen_1_to_verification_Screen2)
//        }
        loginViewModel.loginSuccess.observe(viewLifecycleOwner) { isSuccess ->
            Log.d("LoginScreen", "onViewStateRestored: isSuccess value is :${isSuccess}")
            if (isSuccess == true) {
                loginSignupActivity.loginSuccessfully()
            } else {
                Log.e("LoginScreen", "isSuccess value is:${isSuccess}")
            }
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
            login_signup_screen_1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val id = account.idToken
                if (id != null) {
                    loginViewModel.googleAuth(id)
                } else {
                    Log.e("GoogleLogin", "ID token is null")
                }
            } catch (e: Exception) {
                Log.e(TAG, "onActivityResult: Error occured : ${e.message}")
            }
        }
    }
}