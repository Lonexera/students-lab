// TODO remove this and maybe replace with multibinding
@file:Suppress("WildcardImport", "NoWildcardImports")
package com.example.plantsapp.presentation.ui.worker

import android.content.Context
import androidx.work.*
import com.example.plantsapp.presentation.ui.notification.NotificationWorker
import com.example.plantsapp.presentation.ui.utils.calculateDelay
import java.util.Calendar
import java.util.concurrent.TimeUnit

class TasksWorkManager(appContext: Context) {
    private val workManager = WorkManager.getInstance(appContext)

    fun startNotificationWork(startDate: Calendar) {
        workManager.enqueueUniquePeriodicWork(
            NotificationWorker.NOTIFICATION_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            getEveryDayWorkRequest<NotificationWorker>(
                startDate = startDate,
                startHour = HOUR_OF_NOTIFICATION_WORK_STARTING
            )
        )
    }

    fun startReschedulingWork(startDate: Calendar) {
        workManager.enqueueUniquePeriodicWork(
            ReschedulingWorker.RESCHEDULING_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            getEveryDayWorkRequest<ReschedulingWorker>(
                startDate = startDate,
                startHour = HOUR_OF_RESCHEDULING_WORK_STARTING
            )
        )
    }

    fun cancelAllWork() {
        workManager.cancelAllWork()
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
