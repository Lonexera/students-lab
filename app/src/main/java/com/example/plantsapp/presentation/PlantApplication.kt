package com.example.plantsapp.presentation

import android.app.Application
import com.example.plantsapp.BuildConfig
import com.example.plantsapp.data.RoomPlantsDatabase
import com.example.plantsapp.data.RoomPlantsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class PlantApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val roomPlantsDatabase: RoomPlantsDatabase by lazy {
        RoomPlantsDatabase.getInstance(
            this,
            applicationScope
        )
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
