package com.pardeep.yogify.presentation.viewModels.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pardeep.yogify.domain.repositorys.AuthRepository
import kotlinx.coroutines.launch

class SignUpViewModel(
    var authRepo: AuthRepository
) : ViewModel() {

    var userName = MutableLiveData<String>("")
    var userEmail = MutableLiveData<String>("")
    var userPassword = MutableLiveData<String>("")

    private var _createUserSuccess = MutableLiveData<Boolean>()
    val createUserSuccess: MutableLiveData<Boolean> = _createUserSuccess

    private var _createUserError = MutableLiveData<Boolean>()
    val createUserError: MutableLiveData<Boolean> = _createUserError

    private var _loginUserSuccess = MutableLiveData<Boolean>()
    val loginUserSuccess: MutableLiveData<Boolean> = _loginUserSuccess

    private var _loginUserError = MutableLiveData<String>()
    val loginUserError: MutableLiveData<String> = _loginUserError


    fun signUp() {
        val email = userEmail.value.toString()
        val password = userPassword.value.toString()
        val userName = userName.value.toString()

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _createUserError.value = false
            Log.e("SignUpViewModel", "signUp: Email is not valid email :${email}")
            return
        }

        if (password.length < 6) {
            _createUserError.value = false
            Log.e("SignUpViewModel", "signUp: Password must be equal or greater than 6")
            return
        }

        viewModelScope.launch {
            val authStatus = authRepo.signUp(email, password)
            if (authStatus == true) {
                _createUserSuccess.value = true
                Log.d("SignUpViewModel", "signUp: User Creation done Successfullt")
            } else {
                _createUserError.value = false
                Log.d("SignUpViewModel", "signUp: User Creation failed")

            }
        }
    }

    fun googleAuth(token: String) {
        viewModelScope.launch {
            val authStatus = authRepo.loginWithGoogle(token)
            if (authStatus) {
                _loginUserSuccess.value = true
            } else {
                _loginUserError.value = "Login Error occurred using google"
                _loginUserSuccess.value = false
            }
        }
    }
}