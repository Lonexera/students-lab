package com.example.plantsapp.presentation

import android.app.Application
import com.example.plantsapp.BuildConfig
import com.example.plantsapp.data.RoomPlantsDatabase
import com.example.plantsapp.data.RoomPlantsRepository
import timber.log.Timber

class PlantApplication : Application() {

    private val roomPlantsDatabase: RoomPlantsDatabase by lazy {
        RoomPlantsDatabase.getInstance(this)
    }
    val roomPlantsRepository: RoomPlantsRepository by lazy {
        RoomPlantsRepository(roomPlantsDatabase.plantDao())
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
