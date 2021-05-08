package com.fittingvroom.di

import com.fittingvroom.model.repository.TestProductRepoImpl
import com.fittingvroom.ui.model.parameters.ModelParametersViewModel
import com.fittingvroom.datasource.parameters.SaveModelParameters
import com.fittingvroom.datasource.parameters.SharedPreferencesImplementation
import com.fittingvroom.ui.model.ModelViewModel
import com.fittingvroom.ui.pick_up.PickUpRvViewModel
import com.fittingvroom.ui.view3d.SceneViewer
import org.koin.dsl.module

val application = module {
    factory<SaveModelParameters> { SharedPreferencesImplementation(get()) }
}

val modelScreen = module {
    factory { ModelViewModel(get()) }
}

val modelParametersScreen = module {
    factory { ModelParametersViewModel(get())}
}

val modelPickUp = module {
    factory { PickUpRvViewModel(TestProductRepoImpl())}
}