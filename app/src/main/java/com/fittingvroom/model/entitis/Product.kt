package com.fittingvroom.model.entitis

data class Product (
    var id:Int,
    var idCategory:Int,
    var name:String="",
    var color:String="",
    var price:Float=.0f,
    var vendorCode:String="",
    var description:String="",
    var size:List<String> = listOf(),
    var img:List<String> = listOf()
)