package com.fittingvroom.ui.pick_up

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.fittingvroom.model.AppState
import com.fittingvroom.model.repository.IProructRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PickUpRvViewModel(private val Repository: IProructRepo) : ViewModel() {

    fun getProducts(id: Int) = liveData(Dispatchers.IO) {
        emit(AppState.Loading(null))
        try {
            emit(AppState.Success(Repository.getProducts(id)))

        } catch (exception: Exception) {
            emit(AppState.Error(exception))
        }
    }

    fun getCategorys() = liveData(Dispatchers.IO) {
        emit(AppState.Loading(null))
        try {
            emit(AppState.Success(Repository.getCategorys()))

        } catch (exception: Exception) {
            emit(AppState.Error(exception))
        }
    }

    fun getProduct(id: Int) = liveData(Dispatchers.IO) {
        emit(AppState.Loading(null))
        try {
            emit(AppState.Success(Repository.getProduct(id)))

        } catch (exception: Exception) {
            emit(AppState.Error(exception))
        }
    }
    fun getFavorites() = liveData(Dispatchers.IO) {
        emit(AppState.Loading(null))
        try {
            emit(AppState.Success(Repository.getFavorites()))

        } catch (exception: Exception) {
            emit(AppState.Error(exception))
        }
    }
    fun getFavorites(id: Int) = liveData(Dispatchers.IO) {
        emit(Repository.getFavorite(id))
    }
    fun setFavorites(id: Int)
    {
        viewModelScope.launch {Repository.setFavorite(id)}

    }
    fun setBasket(id: Int,size:String)
    {
        viewModelScope.launch {Repository.setBasket(id,size)}

    }


}