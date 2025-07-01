package com.pardeep.yogify.ui.viewModels.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pardeep.yogify.repositorys.AuthRepository

class SignUpViewModelFactory(
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)){
            return SignUpViewModel(authRepository) as T
        }
        throw IllegalArgumentException("unknown view model class")
    }
}