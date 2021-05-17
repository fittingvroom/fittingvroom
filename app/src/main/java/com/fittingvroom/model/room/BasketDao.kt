package com.fittingvroom.model.room

import androidx.room.*

@Dao
interface BasketDao {
    @Query("SELECT * FROM BasketEntity")
    suspend fun all(): List<BasketEntity>

    @Query("SELECT * FROM BasketEntity WHERE id =:productId")
    suspend fun getProduct(productId: Int): BasketEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: BasketEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<BasketEntity>)

    @Update
    suspend fun update(entity: BasketEntity)

    @Delete
    suspend fun delete(entity: BasketEntity)



}