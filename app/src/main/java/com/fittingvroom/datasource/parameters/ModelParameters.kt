package com.fittingvroom.datasource.parameters

import com.fittingvroom.data.ModelParametersData

interface ModelParameters {
    suspend fun getParameters() : ModelParametersData
    suspend fun isSaved() : Boolean
}