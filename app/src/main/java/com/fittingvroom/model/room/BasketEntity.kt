package com.fittingvroom.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class BasketEntity (
    @field:PrimaryKey
    @field:ColumnInfo(name = "id") var idProduct: Int,
    @field:ColumnInfo(name = "size") var size: String,
    @field:ColumnInfo(name = "amount")  var amount: Int = 1
)

