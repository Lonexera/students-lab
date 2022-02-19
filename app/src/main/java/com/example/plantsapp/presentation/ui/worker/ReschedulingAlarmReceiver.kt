package com.example.plantsapp.presentation.ui.worker

import android.content.Context
import android.content.Intent
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.di.util.HiltBroadcastReceiver
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.domain.repository.TasksRepository
import com.example.plantsapp.domain.usecase.GetTasksForPlantAndDateUseCase
import com.example.plantsapp.domain.workmanager.TasksWorkManager
import com.example.plantsapp.presentation.ui.utils.minusDays
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class ReschedulingAlarmReceiver : HiltBroadcastReceiver() {

    @Inject
    @FirebaseQualifier
    lateinit var plantsRepository: PlantsRepository

    @Inject
    lateinit var getTasksForPlantAndDateUseCase: GetTasksForPlantAndDateUseCase

    @Inject
    @FirebaseQualifier
    lateinit var tasksRepository: TasksRepository

    @Inject
    lateinit var tasksWorkManager: TasksWorkManager

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)

        val currentDate = Date()
        val pendingResult = goAsync()

        // TODO Remove GlobalScope
        GlobalScope.launch {
            plantsRepository.fetchPlants()
                .forEach { plant ->
                    getTasksForPlantAndDateUseCase(plant, currentDate.minusDays(1))
                        .filter { (_, isCompleted) -> !isCompleted }
                        .forEach { (task, _) ->
                            tasksRepository.updateTask(plant, task, currentDate)
                        }
                }

            tasksWorkManager.startReschedulingWork(startDate = Calendar.getInstance())
            pendingResult.finish()
        }
    }
}
