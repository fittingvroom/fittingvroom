package com.fittingvroom.ui.fitting

import androidx.lifecycle.LiveData
import com.fittingvroom.data.ModelParametersData
import com.fittingvroom.datasource.parameters.SaveModelParameters
import com.fittingvroom.model.AppState
import com.fittingvroom.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class FittingViewModel(
    private val parameters: SaveModelParameters
) : BaseViewModel<AppState<ModelParametersData>>() {

    private val liveDataForViewToObserve: LiveData<AppState<ModelParametersData>> = _mutableLiveData

    fun subscribe(): LiveData<AppState<ModelParametersData>> {
        return liveDataForViewToObserve
    }

    fun getData() {
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(parameters.getParameters()) }
    }

    private fun startInteractor(parameters: ModelParametersData) {
        _mutableLiveData.postValue(AppState.Success(parameters))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(ModelParametersData())
        super.onCleared()
    }
}