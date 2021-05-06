package com.fittingvroom.model.repository

import com.fittingvroom.model.entitis.Category
import com.fittingvroom.model.entitis.Product


class TestProductRepoImpl : IProructRepo {
    val categorys: List<Category> = listOf(
        Category(0, "Джинсы", "file:///android_asset/category/Джинсы.png"),
        Category(1, "Рубашки", "file:///android_asset/category/Рубашки.png"),
        Category(2, "Платья", "file:///android_asset/category/Платья.png"),
        Category(3, "Топы", "file:///android_asset/category/Топы.png"),
        Category(4, "Юбки", "file:///android_asset/category/Юбки.png"),
    )

val prodicts= listOf<Product>(
   Product(0,0,"Джинсы 1","Синии",2000.0f,"56465geter","На любой сезон", listOf("file:///android_asset/Jeans/j1.png")),
   Product(1,0,"Джинсы 2","Синии",3000.0f,"56465geter","На любой сезон", listOf("file:///android_asset/Jeans/j2.png")),
   Product(2,0,"Джинсы 3","Синии",1500.0f,"56eterter","На любой сезон", listOf("file:///android_asset/Jeans/j3.png")),
   Product(3,0,"Джинсы 4","Синии",2000.0f,"56465geter","На любой сезон", listOf("file:///android_asset/Jeans/j4.png")),
   Product(4,0,"Джинсы 5","Синии",5000.0f,"56dgseter","На любой сезон", listOf("file:///android_asset/Jeans/j7.png")),
   Product(5,0,"Джинсы 6","Синии",2000.0f,"56dgsder","На любой сезон", listOf("file:///android_asset/Jeans/j6.png")),
   Product(6,2,"Платье 1001 fkjhgskjfhg","Синии",2000.0f,"56xdgsdgeter","На любой сезон", listOf("file:///android_asset/dress/d1.png")),
   Product(7,2,"Платье 1002","Синии",2000.0f,"56xdgsdgeter","На любой сезон", listOf("file:///android_asset/dress/d2.png")),
   Product(8,2,"Платье 1003","Синии",2000.0f,"56xdgsdgeter","На любой сезон", listOf("file:///android_asset/dress/d3.png")),
   Product(10,2,"Платье 1005","Синии",2000.0f,"56xdgsdgeter","На любой сезон", listOf("file:///android_asset/dress/d5.png")),
   Product(11,2,"Платье 1006","Синии",2000.0f,"56xdgsdgeter","На любой сезон", listOf("file:///android_asset/dress/d6.png")),
   Product(12,2,"Платье 1007","Синии",2000.0f,"56xdgsdgeter","На любой сезон", listOf("file:///android_asset/dress/d7.png")),

)
    override suspend fun getCategorys() = categorys

    override suspend fun getProducts(idCategory: Int) =prodicts.filter { it.idCategory==idCategory }

    override suspend fun getProduct(id: Int) = prodicts.filter { it.id==id }.firstOrNull()
}