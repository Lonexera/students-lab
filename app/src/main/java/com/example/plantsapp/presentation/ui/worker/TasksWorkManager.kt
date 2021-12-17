// TODO remove this and maybe replace with multibinding
@file:Suppress("WildcardImport", "NoWildcardImports")
package com.example.plantsapp.presentation.ui.worker

import android.content.Context
import androidx.work.*
import com.example.plantsapp.presentation.ui.notification.NotificationWorker
import com.example.plantsapp.presentation.ui.utils.calculateDelay
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TasksWorkManager @Inject constructor(
    @ApplicationContext appContext: Context
) {
    private val workManager = WorkManager.getInstance(appContext)

    fun startWork(startDate: Calendar) {
        startNotificationWork(startDate)
        startReschedulingWork(startDate)
    }

    fun cancelAllWork() {
        workManager.cancelAllWork()
    }

    private fun startNotificationWork(startDate: Calendar) {
        workManager.enqueueUniquePeriodicWork(
            NotificationWorker.NOTIFICATION_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            getEveryDayWorkRequest<NotificationWorker>(
                startDate = startDate,
                startHour = HOUR_OF_NOTIFICATION_WORK_STARTING
            )
        )
    }

    private fun startReschedulingWork(startDate: Calendar) {
        workManager.enqueueUniquePeriodicWork(
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
