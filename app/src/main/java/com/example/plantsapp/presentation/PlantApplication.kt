// TODO remove this and maybe replace with multibinding
@file:Suppress("WildcardImport", "NoWildcardImports")
package com.example.plantsapp.presentation

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.example.plantsapp.BuildConfig
import com.example.plantsapp.presentation.ui.notification.NotificationWorker
import com.example.plantsapp.presentation.ui.worker.ReschedulingWorker
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
        }

        startNotificationWorker(
            context = applicationContext,
            startDate = Calendar.getInstance()
        )

        startReschedulingWorker(
            context = applicationContext,
            startDate = Calendar.getInstance()
        )
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private fun startNotificationWorker(context: Context, startDate: Calendar) {
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            NotificationWorker.NOTIFICATION_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            getEveryDayWorkRequest<NotificationWorker>(
                startDate = startDate,
                startHour = HOUR_OF_NOTIFICATION_WORK_STARTING
            )
        )
    }

    private fun startReschedulingWorker(context: Context, startDate: Calendar) {
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            ReschedulingWorker.RESCHEDULING_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            getEveryDayWorkRequest<ReschedulingWorker>(
                startDate = startDate,
                startHour = HOUR_OF_RESCHEDULING_WORK_STARTING
            )
        )
    }

    private inline fun <reified T : ListenableWorker> getEveryDayWorkRequest(
        startHour: Int,
        startDate: Calendar,
    ): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<T>(1, TimeUnit.DAYS)
            .setInitialDelay(
                startDate.calculateDelay(nextDayHour = startHour),
                TimeUnit.MILLISECONDS
            )
            .build()
    }

    companion object {
        private const val HOUR_OF_NOTIFICATION_WORK_STARTING = 10
        private const val HOUR_OF_RESCHEDULING_WORK_STARTING = 0
    }
}
