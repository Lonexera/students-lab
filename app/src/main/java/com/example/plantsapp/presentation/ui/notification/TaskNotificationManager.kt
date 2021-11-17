package com.example.plantsapp.presentation.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.example.plantsapp.R
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TaskNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var notificationId = 0

    fun showTaskNotifications(
        plant: Plant,
        tasks: List<Task>
    ) {
        createChannel(context)

        val notificationPicture = Glide.with(context)
            .asBitmap()
            .load(plant.plantPicture ?: R.drawable.ic_baseline_image_24)
            .submit()
            .get()

        val notifications = tasks.map {
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_plant_24)
                .setContentTitle(plant.name.value)
                .setContentText(
                    context.getNotificationText(
                        plantName = plant.name.value,
                        task = it
                    )
                )
                .setLargeIcon(notificationPicture)
                .setGroup(plant.name.value)
                .build()
        }

        val summaryNotification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_plant_24)
            .setGroup(plant.name.value)
            .setGroupSummary(true)
            .build()

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
