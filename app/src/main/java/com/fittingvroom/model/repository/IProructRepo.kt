package com.fittingvroom.model.repository

import com.fittingvroom.model.entitis.Category
import com.fittingvroom.model.entitis.Product
import com.fittingvroom.model.room.BasketEntity

interface IProructRepo {
    suspend fun getCategorys():List<Category>
    suspend fun getProducts(idCategory:Int):List<Product>?
    suspend fun getProduct(id:Int):Product?
    suspend fun setFavorite (id: Int)
    suspend fun getFavorite (id: Int):Boolean
    suspend fun getFavorites ():List<Product>?
    suspend fun setBasket(id: Int,size:String)
    suspend fun getBasket():List<BasketEntity>
    suspend fun updateBasket(id: Int,size: String,amount:Int)
    suspend fun deleteBasket(id: Int,size: String)






}