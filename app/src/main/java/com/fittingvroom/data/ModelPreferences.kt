package com.fittingvroom.data

data class ModelPreferences(
        val isSaved : Boolean = false,
        val gender : String? = "",
        val height : Float = 0F,
        val chestGirth : Float = 0F,
        val waistGirth : Float = 0F,
        val hipsGirth : Float = 0F,
        val chestWidth : Float = 0F,
        val backWidth : Float = 0F
)