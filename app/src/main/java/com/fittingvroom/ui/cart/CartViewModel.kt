package com.fittingvroom.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fittingvroom.model.repository.IProructRepo
import kotlinx.coroutines.launch

class CartViewModel(private val repository: IProructRepo) : ViewModel() {

    private val _data = MutableLiveData<List<CartData>>()
    val data: LiveData<List<CartData>> = _data
    private val _total = MutableLiveData<String>()
    val total: LiveData<String> = _total
    private val cartDataList: MutableList<CartData> = mutableListOf()

    fun getBasket() {

        viewModelScope.launch {
            cartDataList.clear()
            val basket = repository.getBasket()
            var shopTotal = 0F
            for (entety in basket) {

                val product = repository.getProduct(entety.idProduct)
                val favorit = repository.getFavorite(entety.idProduct)
                product?.let {
                    cartDataList.add(
                        CartData(
                            it.id,
                            it.idCategory,
                            it.name,
                            it.color,
                            it.price.toString(),
                            it.vendorCode,
                            it.description,
                            entety.size,
                            it.img.getOrElse(0) { "" },
                            (it.price * entety.amount).toString(),
                            entety.amount,
                            favorit
                        )
                    )
                    shopTotal += it.price * entety.amount
                }
            }
            _data.value = cartDataList
            _total.value = shopTotal.toString()
        }
    }

    fun deleteBasket(data: CartData) {
        viewModelScope.launch {
            repository.deleteBasket(data.id, data.size)
            getBasket()
        }
    }

    fun favoriteBasket(data: CartData) {
        viewModelScope.launch {
            repository.setFavorite(data.id)
            getBasket()
        }
    }

    fun amountBasket(data: CartData, amount: Int) {
        viewModelScope.launch {
            repository.updateBasket(data.id, data.size, amount)
            getBasket()
        }
    }

}