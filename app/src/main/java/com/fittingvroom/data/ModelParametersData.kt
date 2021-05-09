package com.fittingvroom.data

data class ModelParametersData(
        var isSaved: Boolean = false,
        var gender : String? = "",
        var height : String? = "",
        var chestGirth : String? = "",
        var waistGirth : String? = "",
        var hipsGirth : String? = "",
        var chestWidth : String? = "",
        var backWidth : String? = ""
) {
        fun isNotEmpty() : Boolean {
                return !(gender == null || gender == ""
                        || height == null || height == ""
                        || chestGirth == null || chestGirth == ""
                        || waistGirth == null || waistGirth == ""
                        || hipsGirth == null || hipsGirth == ""
                        || chestWidth == null || chestWidth == ""
                        || backWidth == null || backWidth == "")
        }
}