package com.fittingvroom.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModelViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is model Fragment"
    }
    val text: LiveData<String> = _text
}