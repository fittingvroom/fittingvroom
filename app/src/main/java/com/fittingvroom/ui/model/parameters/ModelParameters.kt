package com.fittingvroom.ui.model.parameters

import com.fittingvroom.data.ModelParametersData

interface ModelParameters {
    suspend fun getParameters() : ModelParametersData
}