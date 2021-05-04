package com.fittingvroom.di

import com.fittingvroom.ui.model.parameters.ModelParametersViewModel
import com.fittingvroom.ui.model.parameters.SaveModelParameters
import com.fittingvroom.ui.model.parameters.SharedPreferencesImplementation
import org.koin.dsl.module

val application = module {
    factory<SaveModelParameters> { SharedPreferencesImplementation(get())}
}

val modelParametersScreen = module {
    factory { ModelParametersViewModel(get())}
}