package com.example.plantsapp.presentation.ui.notification

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.plantsapp.domain.repository.TasksRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.util.Date
import javax.inject.Inject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: TasksRepository
) : CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var notificationManager: TaskNotificationManager

    override suspend fun doWork(): Result {
        val plantsWithTasks =
            repository.getPlantsWithTasksForDate(Date()).first()

        plantsWithTasks.forEach { (plant, tasks) ->
            notificationManager.showTaskNotifications(plant, tasks)
        }


        return Result.success()
    }

    companion object {
        const val NOTIFICATION_WORK_NAME = "com.example.plantsapp.NOTIFICATION_WORK"
    }
}
