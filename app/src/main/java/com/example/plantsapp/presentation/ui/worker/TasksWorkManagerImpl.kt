// TODO remove this and maybe replace with multibinding
@file:Suppress("WildcardImport", "NoWildcardImports")

package com.example.plantsapp.presentation.ui.worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.plantsapp.di.util.HiltBroadcastReceiver
import com.example.plantsapp.domain.workmanager.TasksWorkManager
import com.example.plantsapp.presentation.ui.notification.NotificationAlarmReceiver
import com.example.plantsapp.presentation.ui.utils.getNextDayInEpoch
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject

class TasksWorkManagerImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : TasksWorkManager {
    private val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun startWork(startDate: Calendar) {
        startNotificationWork(startDate)
        startReschedulingWork(startDate)
    }

    override fun cancelAllWork() {
        createIntent<NotificationAlarmReceiver>(
            broadcastId = NOTIFICATION_WORK_REQUEST_CODE,
            flags = 0
        ).also { alarmManager.cancel(it) }

        createIntent<ReschedulingAlarmReceiver>(
            broadcastId = RESCHEDULING_WORK_REQUEST_CODE,
            flags = 0
        ).also { alarmManager.cancel(it) }
    }

    override fun startNotificationWork(startDate: Calendar) {
        createIntent<NotificationAlarmReceiver>(
            broadcastId = NOTIFICATION_WORK_REQUEST_CODE,
            flags = PendingIntent.FLAG_UPDATE_CURRENT
        ).let { intent ->
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(
                    startDate.getNextDayInEpoch(nextDayHour = HOUR_OF_NOTIFICATION_WORK_STARTING),
                    intent
                ),
                intent
            )
        }
    }

    override fun startReschedulingWork(startDate: Calendar) {
        createIntent<ReschedulingAlarmReceiver>(
            broadcastId = RESCHEDULING_WORK_REQUEST_CODE,
            flags = PendingIntent.FLAG_UPDATE_CURRENT
        ).let { intent ->
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(
                    startDate.getNextDayInEpoch(nextDayHour = HOUR_OF_RESCHEDULING_WORK_STARTING),
                    intent
                ),
                intent
            )
        }
    }

    private inline fun <reified T : HiltBroadcastReceiver> createIntent(
        broadcastId: Int,
        flags: Int
    ): PendingIntent = PendingIntent.getBroadcast(
        appContext,
        broadcastId,
        Intent(appContext, T::class.java),
        flags
    )

    companion object {
        private const val HOUR_OF_NOTIFICATION_WORK_STARTING = 10
        private const val HOUR_OF_RESCHEDULING_WORK_STARTING = 0

        private const val NOTIFICATION_WORK_REQUEST_CODE = 434
        private const val RESCHEDULING_WORK_REQUEST_CODE = 343
    }
}
