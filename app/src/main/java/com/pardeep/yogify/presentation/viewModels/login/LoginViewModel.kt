package com.pardeep.yogify.presentation.viewModels.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pardeep.yogify.domain.repositorys.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private var authRepository: AuthRepository
) : ViewModel() {

    val userEmail = MutableLiveData("")
    val userPassword = MutableLiveData("")

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> = _loginError

    fun loginWithEmailAndPassword() {

        val email = userEmail.value?.toString() ?: ""
        val password = userPassword.value?.toString() ?: ""

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Email not matches with sample email
            Log.e("LoginViewModel", "loginWithEmailAndPassword: Email is not valid ${userEmail}")
            _loginError.value = "Invalid Email format"
            return
        }

        // password should be greater than the 6
        if (password.length < 6) {
            Log.e(
                "LoginViewModel",
                "loginWithEmailAndPassword: Password is too short make sure password length equal or greater than 6",
            )
            _loginError.value = "Password must be at least 6 characters"
            return
        }

        // Proceed with Firebase Auth or other login logic
        Log.d("LoginViewModel", "Logging in with $email and $password")
        viewModelScope.launch {
            val authStatus = authRepository.loginWithEmailAndPassword(
                userPassword = password,
                userEmail = email
            )
            if (authStatus) {
                // login successfully
                // navigate to the next screen
                _loginSuccess.value = true
            } else {
                Log.e(
                    "LoginViewModel",
                    "loginWithEmailAndPassword: Status of login :${authStatus} ",
                )
                _loginError.value = "failed. Please check credentials."
                _loginSuccess.value = false
            }
        }


    }

    fun googleAuth(token: String) {
        viewModelScope.launch {
            val authStatus = authRepository.loginWithGoogle(token)
            if (authStatus) {
                _loginSuccess.value = true
            } else {
                _loginError.value = "Login Error occurred using google"
                _loginSuccess.value = false
            }
        }
    }
}