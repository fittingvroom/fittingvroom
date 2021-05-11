package com.fittingvroom.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavoritesEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "id") var idProduct: Int
)