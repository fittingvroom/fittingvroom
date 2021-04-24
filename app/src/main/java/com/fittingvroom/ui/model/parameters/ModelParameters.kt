package com.fittingvroom.ui.model.parameters

import com.fittingvroom.data.ModelPreferences

interface ModelParameters {
    suspend fun getParameters() : ModelPreferences
}