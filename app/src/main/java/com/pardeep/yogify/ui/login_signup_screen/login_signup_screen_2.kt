package com.pardeep.yogify.ui.login_signup_screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentLoginSignupScreen2Binding
import com.pardeep.yogify.repositoryImplementation.AuthRepoUseCase
import com.pardeep.yogify.ui.viewModels.signup.SignUpViewModel
import com.pardeep.yogify.ui.viewModels.signup.SignUpViewModelFactory

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
    lateinit var loginSignupActivity: LoginSignupActivity
    var binding: FragmentLoginSignupScreen2Binding? = null
    lateinit var signUpViewModel: SignUpViewModel
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
        val authRepository = AuthRepoUseCase(FirebaseAuth.getInstance())
        val factory = SignUpViewModelFactory(authRepository)
        signUpViewModel =
            ViewModelProvider(requireActivity(), factory)[SignUpViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login_signup_screen_2,
            container,
            false
        )
        binding?.lifecycleOwner = this
        binding?.signUpViewModel = signUpViewModel
        return binding?.root
//        return inflater.inflate(R.layout.fragment_login_signup_screen_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOption)
        val signInIntent = googleSignInClient.signInIntent

        binding?.signUp?.setOnClickListener {
            signUpViewModel.signUp()
        }

        signUpViewModel.createUserSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess == true) {
                Toast.makeText(requireContext(), "User create Successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.e("LoginScreen2", "onViewCreated: User creation failed")
            }

        }
        signUpViewModel.loginUserSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess == true) {
                Toast.makeText(requireContext(), "User login with google", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.e("LoginScreen2", "onViewCreated: User creation failed")
            }

        }

        //navigate to verification screen if user chooses to sign up with google or facebook or twitter
        binding?.googleBtn?.setOnClickListener {
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
        }
//        binding?.facebookBtn?.setOnClickListener {
//            navController.navigate(R.id.action_login_signup_screen_2_to_verification_Screen2)
//        }
//        binding?.twitterBtn?.setOnClickListener {
//            navController.navigate(R.id.action_login_signup_screen_2_to_verification_Screen2)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                val id = account.id
                if (id != null) {
                    signUpViewModel.googleAuth(id)
                }
            } catch (e: Exception) {
                Log.e("GoogleLogin", "ID token is null in SignUpScreen")
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