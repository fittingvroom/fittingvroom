package com.fittingvroom.di

import android.app.Application
import androidx.room.Room
import com.fittingvroom.datasource.parameters.SaveModelParameters
import com.fittingvroom.datasource.parameters.SharedPreferencesImplementation
import com.fittingvroom.ui.fitting.FittingViewModel
import com.fittingvroom.model.repository.IProructRepo
import com.fittingvroom.model.repository.TestProductRepoImpl
import com.fittingvroom.model.room.FittingDatabase
import com.fittingvroom.notifications.FirebaseNotificationService
import com.fittingvroom.notifications.NotificationService
import com.fittingvroom.ui.cart.CartViewModel
import com.fittingvroom.ui.model.ModelViewModel
import com.fittingvroom.ui.model.parameters.ModelParametersViewModel
import com.fittingvroom.ui.pick_up.PickUpRvViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {
    factory<SaveModelParameters> { SharedPreferencesImplementation(get()) }
    factory<NotificationService> { FirebaseNotificationService() }
}

val modelScreen = module {
    factory { ModelViewModel(get()) }
}

val modelParametersScreen = module {
    factory { ModelParametersViewModel(get()) }
}

val repositoryModule = module {

    fun provideCountryRepository(database: FittingDatabase): IProructRepo {
        return TestProductRepoImpl(database)
    }
    single { provideCountryRepository(get()) }
}

val fittingScreen = module {
    factory { FittingViewModel(get()) }
}

val modelPickUp = module {
    viewModel { PickUpRvViewModel(get()) }
    viewModel { CartViewModel(get()) }
}

val databaseModule = module {
    fun provideDatabase(application: Application): FittingDatabase {
        return Room.databaseBuilder(application, FittingDatabase::class.java, "db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { provideDatabase(get()) }
}