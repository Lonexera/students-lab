package com.example.plantsapp

import android.app.Application
import timber.log.Timber

class PlantApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
