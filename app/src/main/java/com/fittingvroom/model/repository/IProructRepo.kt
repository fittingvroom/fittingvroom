package com.fittingvroom.model.repository

import com.fittingvroom.model.entitis.Category
import com.fittingvroom.model.entitis.Product

interface IProructRepo {
    suspend fun getCategorys():List<Category>
    suspend fun getProducts(idCategory:Int):List<Product>?
    suspend fun getProduct(id:Int):Product?
}