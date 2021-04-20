package com.fittingvroom.ui.fitting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FittingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is fitting Fragment"
    }
    val text: LiveData<String> = _text
}