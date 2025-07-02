package com.pardeep.yogify.presentation.viewModels.setupScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pardeep.yogify.local.preference.UsesPreference

class SetupActivityViewModel(
   private val usesPreference: UsesPreference
) : ViewModel() {

    private var _progressBarValue = MutableLiveData<Int>(0)

    val progressBarValue: MutableLiveData<Int> = _progressBarValue

    fun incrementProgressbarValue(newValue: Int) {
        _progressBarValue.value = newValue
    }

    fun decrementProgressValue(newValue: Int) {
        val currentValue = _progressBarValue.value
        if (currentValue != null) {
            _progressBarValue.value = currentValue - newValue
        }
    }

    fun updateSetupStatus(status: Boolean) {
        usesPreference.setupCompleteStatus(status)
    }


}