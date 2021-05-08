package com.fittingvroom.app

import android.app.Application
import com.fittingvroom.di.application
import com.fittingvroom.di.modelParametersScreen
import com.fittingvroom.di.modelPickUp
import com.fittingvroom.di.modelScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FittingVRoomApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(
                application,
                modelParametersScreen,
                modelPickUp,
                modelScreen))
        }
    }
}