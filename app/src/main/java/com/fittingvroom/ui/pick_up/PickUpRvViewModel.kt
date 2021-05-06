package com.fittingvroom.ui.pick_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.fittingvroom.model.AppState
import com.fittingvroom.model.repository.IProructRepo
import kotlinx.coroutines.Dispatchers

class PickUpRvViewModel(private val Repository: IProructRepo) : ViewModel() {

    fun getProduct(id: Int) = liveData(Dispatchers.IO) {
        emit(AppState.Loading(null))
        try {
            emit(AppState.Success(Repository.getProducts(id)))

        } catch (exception: Exception) {
            emit(AppState.Error(exception))
        }
    }
}