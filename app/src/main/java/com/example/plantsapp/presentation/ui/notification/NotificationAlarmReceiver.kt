package com.example.plantsapp.presentation.ui.notification

import android.content.Context
import android.content.Intent
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.di.util.HiltBroadcastReceiver
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.domain.usecase.GetTasksForPlantAndDateUseCase
import com.example.plantsapp.domain.workmanager.TasksWorkManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class NotificationAlarmReceiver : HiltBroadcastReceiver() {

    @Inject
    @FirebaseQualifier
    lateinit var plantsRepository: PlantsRepository

    @Inject
    lateinit var getTasksForPlantAndDateUseCase: GetTasksForPlantAndDateUseCase

    @Inject
    lateinit var notificationManager: TaskNotificationManager

    @Inject
    lateinit var tasksWorkManager: TasksWorkManager

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)

        val pendingResult = goAsync()

        // TODO Remove GlobalScope
        GlobalScope.launch {
            val plantsWithTasks =
                plantsRepository.fetchPlants()
                    .map { plant ->
                        plant to getTasksForPlantAndDateUseCase(plant, Date())
                    }
                    .filter { it.second.isNotEmpty() }

            plantsWithTasks.forEach { (plant, tasksWithState) ->
                val tasks = tasksWithState
                    .filter { (_, isCompleted) -> !isCompleted }
                    .map { (task, _) -> task }

                if (tasks.isNotEmpty()) {
                    notificationManager.showTaskNotifications(plant, tasks)
                }
            }

            tasksWorkManager.startNotificationWork(startDate = Calendar.getInstance())
            pendingResult.finish()
        }
    }
}
