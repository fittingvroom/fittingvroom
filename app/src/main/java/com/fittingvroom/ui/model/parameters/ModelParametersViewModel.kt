package com.fittingvroom.ui.model.parameters

import androidx.lifecycle.LiveData
import com.fittingvroom.data.ModelParametersData
import com.fittingvroom.datasource.parameters.SaveModelParameters
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

    fun putData(modelParametersData: ModelParametersData) : Boolean {
        val result = modelParametersData.isNotEmpty()
        if (result) {
            viewModelCoroutineScope.launch {
                saveParameters(modelParametersData)
            }
        }
        return result
    }

    suspend fun saveParameters(modelParametersData: ModelParametersData) {
        if (modelParametersData.isNotEmpty()) {
            parameters.putParameters(modelParametersData, true)
        }
    }

    override fun handleError(error: Throwable) {
    }
}
