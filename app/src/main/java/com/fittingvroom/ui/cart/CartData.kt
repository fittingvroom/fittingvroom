package com.fittingvroom.ui.cart

class CartData(
    var id: Int,
    var idCategory: Int,
    var name: String = "",
    var color: String = "",
    var price: Float = .0f,
    var vendorCode: String = "",
    var description: String = "",
    var size: String = "",
    var img: String="",
    var total:Float,
    var amount: Int = 1,
    var favorite:Boolean=false

)