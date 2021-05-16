package com.fittingvroom.ui.cart

data class CartData(
    var id: Int,
    var idCategory: Int,
    var name: String = "",
    var color: String = "",
    var price: String ="",
    var vendorCode: String = "",
    var description: String = "",
    var size: String = "",
    var img: String="",
    var total:String,
    var amount: Int = 1,
    var favorite:Boolean=false

)