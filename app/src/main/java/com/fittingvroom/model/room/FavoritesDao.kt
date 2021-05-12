package com.fittingvroom.model.room

import androidx.room.*

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM FavoritesEntity")
    suspend fun all(): List<FavoritesEntity>?

    @Query("SELECT * FROM FavoritesEntity WHERE id =:id")
    suspend fun getProduct(id: Int): FavoritesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoritesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<FavoritesEntity>)

    @Update
    suspend fun update(entity: FavoritesEntity)

    @Delete
    suspend fun delete(entity: FavoritesEntity)
}