package com.example.plantsapp.presentation.ui.notification.broadcastreceiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskKeys
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.domain.usecase.CompleteTaskUseCase
import com.example.plantsapp.presentation.ui.notification.TaskNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

abstract class HiltBroadcastReceiver : BroadcastReceiver() {
    @CallSuper
    override fun onReceive(context: Context, intent: Intent?) = Unit
}

@AndroidEntryPoint
class NotificationBroadcastReceiver : HiltBroadcastReceiver() {

    @Inject
    lateinit var completeTaskUseCase: CompleteTaskUseCase
    @Inject
    lateinit var taskNotificationManager: TaskNotificationManager
    @Inject
    @FirebaseQualifier
    lateinit var plantsRepository: PlantsRepository

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        val pendingResult = goAsync()

        intent?.let {
            val task = TaskKeys.getFromKey(it.taskKey)
                .toTask(
                    taskId = it.taskId,
                    frequency = it.taskFrequency,
                    lastUpdateDate = it.taskUpdateDate
                )

            // TODO Remove GlobalScope
            GlobalScope.launch {
                completeTaskUseCase(
                    plant = plantsRepository.getPlantByName(it.plantName),
                    task = task,
                    completionDate = Date()
                )
                taskNotificationManager.cancelNotification(it.notificationId)
                pendingResult.finish()
            }
        }
    }

    companion object {

        fun createCompletePendingIntent(
            context: Context,
            task: Task,
            notificationId: Int,
            requestCode: Int,
            plantName: Plant.Name
        ): PendingIntent {
            val completeIntent = Intent(context, NotificationBroadcastReceiver::class.java)
                .apply {
                    this.notificationId = notificationId
                    taskId = task.id
                    taskFrequency = task.frequency
                    taskKey = TaskKeys.from(task).key
                    taskUpdateDate = task.lastUpdateDate
                    this.plantName = plantName
                }
            return PendingIntent.getBroadcast(
                context,
                requestCode,
                completeIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        private var Intent.taskId: Int
            get() = getIntExtra(EXTRA_TASK_ID, 0)
            set(taskId) {
                putExtra(EXTRA_TASK_ID, taskId)
            }

        private var Intent.taskFrequency: Int
            get() = getIntExtra(EXTRA_TASK_FREQUENCY, 0)
            set(frequency) {
                putExtra(EXTRA_TASK_FREQUENCY, frequency)
            }

        private var Intent.taskUpdateDate: Date
            get() = getSerializableExtra(EXTRA_TASK_UPDATE_DATE) as Date
            set(updateDate) {
                putExtra(EXTRA_TASK_UPDATE_DATE, updateDate)
            }

        private var Intent.taskKey: String
            get() = getStringExtra(EXTRA_TASK_KEY)
                ?: error("Task Key Was Not Passed!")
            set(taskKey) {
                putExtra(EXTRA_TASK_KEY, taskKey)
            }

        private var Intent.notificationId: Int
            get() = getIntExtra(EXTRA_NOTIFICATION_ID, 0)
            set(notificationId) {
                putExtra(EXTRA_NOTIFICATION_ID, notificationId)
            }

        private var Intent.plantName: Plant.Name
            get() = Plant.Name(
                getStringExtra(EXTRA_PLANT_NAME)
                ?: error("Plant Name Was Not Passed!")
            )
            set(plantName) {
                putExtra(EXTRA_PLANT_NAME, plantName.value)
            }

        private const val EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID"
        private const val EXTRA_TASK_ID = "EXTRA_TASK_ID"
        private const val EXTRA_TASK_KEY = "EXTRA_TASK_KEY"
        private const val EXTRA_TASK_FREQUENCY = "EXTRA_FREQUENCY"
        private const val EXTRA_TASK_UPDATE_DATE = "EXTRA_TASK_UPDATE_DATE"
        private const val EXTRA_PLANT_NAME = "EXTRA_PLANT_NAME"
    }
}
