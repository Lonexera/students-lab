package com.example.plantsapp.presentation.ui.notification

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.plantsapp.domain.repository.TasksRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Date

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: TasksRepository,
    private val notificationManager: TaskNotificationManager
) : CoroutineWorker(context, workerParams) {

    // TODO show notifications only for uncompleted tasks
    override suspend fun doWork(): Result {
        val plantsWithTasks =
            repository.getPlantsWithTasksForDate(Date())

        plantsWithTasks.forEach { (plant, tasksWithState) ->
            val tasks = tasksWithState.map { it.task }
            notificationManager.showTaskNotifications(plant, tasks)
        }

        return Result.success()
    }

    companion object {
        const val NOTIFICATION_WORK_NAME = "com.example.plantsapp.NOTIFICATION_WORK"
    }
}
