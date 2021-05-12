package com.fittingvroom.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(BasketEntity::class, FavoritesEntity::class),
    version = 1,
    exportSchema = false
)
abstract class FittingDatabase : RoomDatabase(){
    abstract fun BasketDao(): BasketDao
    abstract fun FavoritesDao(): FavoritesDao
}

