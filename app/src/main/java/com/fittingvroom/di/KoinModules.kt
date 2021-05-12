package com.fittingvroom.di

import android.app.Application
import androidx.room.Room
import com.fittingvroom.datasource.parameters.SaveModelParameters
import com.fittingvroom.datasource.parameters.SharedPreferencesImplementation
import com.fittingvroom.model.repository.IProructRepo
import com.fittingvroom.model.repository.TestProductRepoImpl
import com.fittingvroom.model.room.FittingDatabase
import com.fittingvroom.ui.model.ModelViewModel
import com.fittingvroom.ui.model.parameters.ModelParametersViewModel
import com.fittingvroom.ui.pick_up.PickUpRvViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {
    factory<SaveModelParameters> { SharedPreferencesImplementation(get()) }
}

val modelScreen = module {
    factory { ModelViewModel(get()) }
}

val modelParametersScreen = module {
    factory { ModelParametersViewModel(get()) }
}
val repositoryModule = module {

    fun provideCountryRepository( database : FittingDatabase): IProructRepo {
        return TestProductRepoImpl( database)
    }
    single { provideCountryRepository(get()) }

}



val modelPickUp = module {
    viewModel { PickUpRvViewModel(get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): FittingDatabase {
        return Room.databaseBuilder(application, FittingDatabase::class.java, "db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { provideDatabase(get()) }

}