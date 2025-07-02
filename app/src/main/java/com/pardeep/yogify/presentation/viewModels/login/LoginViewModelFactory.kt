package com.pardeep.yogify.presentation.viewModels.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pardeep.yogify.domain.repositorys.AuthRepository

class LoginViewModelFactory(
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(authRepository) as T
        }
        throw IllegalArgumentException("unknown view model class")
    }
}