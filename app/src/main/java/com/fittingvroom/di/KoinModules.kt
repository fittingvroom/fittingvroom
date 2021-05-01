package com.fittingvroom.di

import com.fittingvroom.ui.model.parameters.ModelParametersViewModel
import com.fittingvroom.ui.model.parameters.SharedPreferencesImplementation
import org.koin.dsl.module

val application = module {
    factory { SharedPreferencesImplementation(get())}
}

val modelParametersScreen = module {
    factory { ModelParametersViewModel(get())}
}