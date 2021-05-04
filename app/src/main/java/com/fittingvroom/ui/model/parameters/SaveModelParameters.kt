package com.fittingvroom.ui.model.parameters

import com.fittingvroom.data.ModelParametersData

interface SaveModelParameters : ModelParameters {
    fun putParameters(modelParametersData: ModelParametersData, allParmetersOk : Boolean) : Boolean
}