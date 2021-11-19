package com.example.plantsapp.presentation.ui.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.plantsapp.R
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.presentation.ui.utils.getBitmapWithPlaceholder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TaskNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var notificationId = 0

    init {
        createChannel(context)
    }

    fun showTaskNotifications(
        plant: Plant,
        tasks: List<Task>
    ) {
        val notificationPicture = plant.plantPicture.getBitmapWithPlaceholder(context)

        val notifications = tasks.map {
            prepareTaskNotification(
                plantName = plant.name.value,
                plantPicture = notificationPicture,
                task = it
            )
        }

        val summaryNotification = prepareSummaryNotification(plant.name.value)

        notifications.forEach {
            NotificationManagerCompat.from(context).notify(notificationId++, it)
        }
        NotificationManagerCompat.from(context).notify(notificationId++, summaryNotification)
    }

    private fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.title_notification_channel)
            val description = context.getString(R.string.msg_notification_description)
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                name,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = description

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun prepareTaskNotification(
        plantName: String,
        plantPicture: Bitmap,
        task: Task
    ): Notification {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_plant_24)
            .setContentTitle(plantName)
            .setContentText(
                context.getNotificationText(
                    plantName = plantName,
                    task = task
                )
            )
            .setLargeIcon(plantPicture)
            .setGroup(plantName)
            .build()
    }

    private fun prepareSummaryNotification(
        plantName: String
    ): Notification {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_plant_24)
            .setGroup(plantName)
            .setGroupSummary(true)
            .build()
    }

    private fun Context.getNotificationText(plantName: String, task: Task): String {
        return getString(
            when (task) {
                is Task.WateringTask -> R.string.msg_notification_task_watering
                is Task.SprayingTask -> R.string.msg_notification_task_spraying
                is Task.LooseningTask -> R.string.msg_notification_task_loosening
            },
            plantName
        )
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "CHANNEL_TASK_NOTIFICATION_ID"
    }
}
