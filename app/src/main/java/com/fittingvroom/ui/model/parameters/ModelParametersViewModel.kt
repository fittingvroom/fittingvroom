package com.fittingvroom.ui.model.parameters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fittingvroom.data.ModelParametersData
import kotlinx.coroutines.*

class ModelParametersViewModel(
    private val parameters: SaveModelParameters
) : ViewModel() {

    private val _mutableLiveData = MutableLiveData<ModelParametersData>()
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

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    private fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    private fun handleError(error: Throwable) {

    }

    fun saveParameters(modelParametersData: ModelParametersData) : Boolean{
        return parameters.putParameters(modelParametersData, dataIsNotEmpty(modelParametersData))
    }

    private fun dataIsNotEmpty(saveData: ModelParametersData): Boolean {
        return !(saveData.gender == null || saveData.gender == ""
                || saveData.height == null || saveData.height == ""
                || saveData.chestGirth == null || saveData.chestGirth == ""
                || saveData.waistGirth == null || saveData.waistGirth == ""
                || saveData.hipsGirth == null || saveData.hipsGirth == ""
                || saveData.chestWidth == null || saveData.chestWidth == ""
                || saveData.backWidth == null || saveData.backWidth == "")
    }
}
