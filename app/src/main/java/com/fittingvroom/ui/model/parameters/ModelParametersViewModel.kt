package com.fittingvroom.ui.model.parameters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fittingvroom.data.ModelParametersData
import com.fittingvroom.ui.base.BaseViewModel
import kotlinx.coroutines.*

class ModelParametersViewModel(
    private val parameters: SaveModelParameters
) : BaseViewModel<ModelParametersData>() {

    private val liveDataForViewToObserve: LiveData<ModelParametersData> = _mutableLiveData

    fun subscribe(): LiveData<ModelParametersData> {
        return liveDataForViewToObserve
    }

    fun getData() {
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(parameters.getParameters()) }
    }

    private fun startInteractor(parameters: ModelParametersData) {
        _mutableLiveData.postValue(parameters)
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    fun saveParameters(modelParametersData: ModelParametersData) : Boolean{
        return parameters.putParameters(modelParametersData, modelParametersData.isNotEmpty())
    }

    override fun handleError(error: Throwable) {
    }
}
