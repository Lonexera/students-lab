package com.example.plantsapp.presentation

import android.app.Application
import com.example.plantsapp.BuildConfig
import timber.log.Timber

class PlantApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
