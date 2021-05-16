package com.fittingvroom.model.repository

import com.fittingvroom.model.entitis.Category
import com.fittingvroom.model.entitis.Product
import com.fittingvroom.model.room.BasketEntity
import com.fittingvroom.model.room.FavoritesEntity
import com.fittingvroom.model.room.FittingDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TestProductRepoImpl(val database: FittingDatabase) : IProructRepo {
    val categorys: List<Category> = listOf(
        Category(0, "Джинсы", "file:///android_asset/category/джинсы.png"),
        Category(1, "Рубашки", "file:///android_asset/category/рубашки.png"),
        Category(2, "Платья", "file:///android_asset/category/платья.png"),
        Category(3, "Топы", "file:///android_asset/category/футболки.png"),
        Category(4, "Юбки", "file:///android_asset/category/юбки.png"),
    )

    val prodicts = listOf<Product>(
        Product(
            0,
            0,
            "Джинсы 1",
            "Синий",
            2000.0f,
            "56465geter",
            "На любой сезон",
            listOf("L", "M", "XL", "XXL"),
            listOf("file:///android_asset/Jeans/j1.png", "file:///android_asset/Jeans/j5.png")
        ),
        Product(
            1,
            0,
            "Джинсы 2",
            "Синий",
            3000.0f,
            "56465geter",
            "На любой сезон",
            listOf("L"),
            listOf("file:///android_asset/Jeans/j2.png")
        ),
        Product(
            2,
            0,
            "Джинсы 3",
            "Синий",
            1500.0f,
            "56eterter",
            "На любой сезон",
            listOf("L", "M", "XL"),
            listOf("file:///android_asset/Jeans/j3.png")
        ),
        Product(
            3,
            0,
            "Джинсы 4",
            "Синий",
            2000.0f,
            "56465geter",
            "На любой сезон",
            listOf("L", "M", "XL"),
            listOf("file:///android_asset/Jeans/j4.png")
        ),
        Product(
            4,
            0,
            "Джинсы 5",
            "Синий",
            5000.0f,
            "56dgseter",
            "На любой сезон",
            listOf("L", "M", "XL"),
            listOf("file:///android_asset/Jeans/j7.png")
        ),
        Product(
            5,
            0,
            "Джинсы 6",
            "Синий",
            2000.0f,
            "56dgsder",
            "На любой сезон",
            listOf("L", "M", "XL"),
            listOf("file:///android_asset/Jeans/j6.png")
        ),
        Product(
            6,
            2,
            "Платье 1001",
            "Синий",
            2000.0f,
            "56xdgsdgeter",
            "На любой сезон",
            listOf("L", "M", "XL"),
            listOf("file:///android_asset/dress/d1.png")
        ),
        Product(
            7,
            2,
            "Платье 1002",
            "Синий",
            2000.0f,
            "56xdgsdgeter",
            "На любой сезон",
            listOf("L", "M", "XL"),
            listOf("file:///android_asset/dress/d2.png")
        ),
        Product(
            8,
            2,
            "Платье 1003",
            "Синий",
            2000.0f,
            "56xdgsdgeter",
            "На любой сезон",
            listOf("L", "M", "XL"),
            listOf("file:///android_asset/dress/d3.png")
        ),
        Product(
            10,
            2,
            "Платье 1005",
            "Синий",
            2000.0f,
            "56xdgsdgeter",
            "На любой сезон",
            listOf("L", "M", "XL"),
            listOf("file:///android_asset/dress/d5.png")
        ),
        Product(
            11,
            2,
            "Платье 1006",
            "Синий",
            2000.0f,
            "56xdgsdgeter",
            "На любой сезон",
            listOf("L", "M", "XL"),
            listOf("file:///android_asset/dress/d6.png")
        ),
        Product(
            12,
            2,
            "Платье 1007",
            "Синий",
            2000.0f,
            "56xdgsdgeter",
            "На любой сезон",
            listOf("L", "M", "XL"),
            listOf("file:///android_asset/dress/d7.png")
        ),

        )

    override suspend fun getCategorys(): List<Category> {
        Thread.sleep(1000)
        return categorys
    }

    override suspend fun getProducts(idCategory: Int): List<Product> {
        Thread.sleep(500)
        return prodicts.filter { it.idCategory == idCategory }
    }

    override suspend fun getProduct(id: Int) = prodicts.filter { it.id == id }.firstOrNull()

    override suspend fun getFavorites() = prodicts.filter { product ->
        database.FavoritesDao().all()?.any { it.idProduct == product.id }!!
    }

    override suspend fun setFavorite(id: Int) {

        if (database.FavoritesDao().getProduct(id) == null)
            database.FavoritesDao().insert(FavoritesEntity(id))
        else
            database.FavoritesDao().delete(FavoritesEntity(id))

    }

    override suspend fun getFavorite(id: Int): Boolean {
        return database.FavoritesDao().getProduct(id) != null
    }

    override suspend fun setBasket(id: Int, size: String) {
        database.BasketDao().insert(BasketEntity(id, size, 1))


    }

    override suspend fun getBasket(): List<BasketEntity> {
        return database.BasketDao().all()
    }

    override suspend fun updateBasket(id: Int,size: String,amount:Int) {
        database.BasketDao().update(BasketEntity(id,size,amount))
    }

    override suspend fun deleteBasket(id: Int,size: String) {
        database.BasketDao().delete(BasketEntity(id,size))
    }


}