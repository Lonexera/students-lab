package com.example.plantsapp.presentation

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.example.plantsapp.BuildConfig
import com.example.plantsapp.presentation.ui.notification.NotificationWorker
import com.example.plantsapp.presentation.ui.utils.calculateDelay
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class PlantApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            Thread {
                Glide.get(this).clearDiskCache()
            }.start()
            Glide.get(this).clearMemory()
        }

        startNotificationWorker(
            context = applicationContext,
            startDate = Calendar.getInstance()
        )
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private fun startNotificationWorker(context: Context, startDate: Calendar) {
        val notificationRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(
                    startDate.calculateDelay(nextDayHour = HOUR_OF_WORK_STARTING),
                    TimeUnit.MILLISECONDS
                )
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            NotificationWorker.NOTIFICATION_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            notificationRequest
        )
    }

    companion object {
        private const val HOUR_OF_WORK_STARTING = 10
    }
}
