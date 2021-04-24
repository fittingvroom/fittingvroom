package com.fittingvroom.ui.model.parameters

import com.fittingvroom.data.ModelPreferences

interface SaveModelParameters : ModelParameters {
    suspend fun putParameters(modelPreferences: ModelPreferences)
}