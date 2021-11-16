package com.example.plantsapp.presentation.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.example.plantsapp.R
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Date

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: TasksRepository
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        runBlocking {
            val plantsWithTasks =
                repository.getPlantsWithTasksForDate(Date()).first()

            plantsWithTasks.forEach { (plant, tasks) ->
                sendTaskNotifications(plant, tasks)
            }
        }

        return Result.success()
    }

    private fun sendTaskNotifications(
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
                .setNumber(NOTIFICATION_ID)
                .setSmallIcon(R.drawable.ic_plant_24)
                .setContentTitle(plant.name.value)
                .setContentText(
                    context.getNotificationText(
                        plantName = plant.name.value,
                        task = it
                    )
                )
                .setLargeIcon(notificationPicture)
                .setGroup(NOTIFICATION_GROUP_KEY) // TODO make it unique for each plant
                .build()
        }

        val summaryNotification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_plant_24)
            .setGroup(NOTIFICATION_GROUP_KEY)
            .setGroupSummary(true)
            .build()

        notifications.forEach {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID++, it)
        }
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID++, summaryNotification)
    }

    private fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.title_notification_channel)
            val description = context.getString(R.string.msg_notification_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
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
        const val NOTIFICATION_WORK_NAME = "com.example.plantsapp.NOTIFICATION_WORK"

        private const val NOTIFICATION_CHANNEL_ID = "CHANNEL_TASK_NOTIFICATION_ID"
        private var NOTIFICATION_ID = 213
        private const val NOTIFICATION_GROUP_KEY = "GROUP_PLANT_TASKS"
    }
}
