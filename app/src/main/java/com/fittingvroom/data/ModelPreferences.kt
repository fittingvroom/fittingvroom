package com.fittingvroom.data

data class ModelPreferences(
        val isSaved : Boolean = false,
        val gender : String? = "",
        val height : String? = "",
        val chestGirth : String? = "",
        val waistGirth : String? = "",
        val hipsGirth : String? = "",
        val chestWidth : String? = "",
        val backWidth : String? = ""
)