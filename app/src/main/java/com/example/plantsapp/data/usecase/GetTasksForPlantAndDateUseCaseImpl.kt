package com.example.plantsapp.data.usecase

import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantsapp.domain.repository.TasksRepository
import com.example.plantsapp.domain.usecase.GetTasksForPlantAndDateUseCase
import com.example.plantsapp.presentation.ui.utils.isDueDate
import java.util.Date
import javax.inject.Inject

class GetTasksForPlantAndDateUseCaseImpl @Inject constructor(
    @FirebaseQualifier private val tasksRepository: TasksRepository,
    @FirebaseQualifier private val tasksHistoryRepository: TasksHistoryRepository
) : GetTasksForPlantAndDateUseCase {

    override suspend operator fun invoke(plant: Plant, currentDate: Date): List<Pair<Task, Boolean>> {
        return tasksRepository.getTasksForPlant(plant)
            .filter { task ->
                currentDate.isDueDate(
                    startDate = task.lastUpdateDate,
                    intervalDays = task.frequency
                )
            }
            .map { task ->
                task to tasksHistoryRepository.isTaskCompletedForDate(plant, task, currentDate)
            }
    }
}
