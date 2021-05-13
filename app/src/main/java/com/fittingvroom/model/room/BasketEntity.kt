package com.fittingvroom.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = arrayOf("id", "size"))
class BasketEntity(
    @field:ColumnInfo(name = "id") var idProduct: Int,
    @field:ColumnInfo(name = "size") var size: String = "",
    @field:ColumnInfo(name = "amount") var amount: Int = 1
)

