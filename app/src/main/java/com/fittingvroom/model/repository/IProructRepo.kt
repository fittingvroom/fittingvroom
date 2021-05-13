package com.fittingvroom.model.repository

import com.fittingvroom.model.entitis.Category
import com.fittingvroom.model.entitis.Product

interface IProructRepo {
    suspend fun getCategorys():List<Category>
    suspend fun getProducts(idCategory:Int):List<Product>?
    suspend fun getProduct(id:Int):Product?
    suspend fun setFavorite (id: Int)
    suspend fun getFavorite (id: Int):Boolean
    suspend fun getFavorites ():List<Product>?
    suspend fun setBasket(id: Int,size:String)



}