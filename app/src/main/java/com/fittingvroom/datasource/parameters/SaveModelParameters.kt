package com.fittingvroom.datasource.parameters

import com.fittingvroom.data.ModelParametersData

interface SaveModelParameters : ModelParameters {
    suspend fun putParameters(modelParametersData: ModelParametersData, allParmetersOk : Boolean) : Boolean
}