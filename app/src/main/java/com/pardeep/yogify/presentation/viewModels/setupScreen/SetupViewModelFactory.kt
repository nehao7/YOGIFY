package com.pardeep.yogify.presentation.viewModels.setupScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pardeep.yogify.local.preference.UsesPreference

class SetupViewModelFactory(
    private val usesPreference: UsesPreference
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SetupActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SetupActivityViewModel(usesPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
