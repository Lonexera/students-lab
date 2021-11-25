package com.example.plantsapp.data.usecase

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.TaskWithState
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantsapp.domain.repository.TasksRepository
import com.example.plantsapp.domain.usecase.GetTasksForPlantForDateUseCase
import com.example.plantsapp.presentation.ui.utils.isDueDate
import java.util.Date
import javax.inject.Inject

class GetTasksForPlantForDateUseCaseImpl @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val tasksHistoryRepository: TasksHistoryRepository
) : GetTasksForPlantForDateUseCase {

    override suspend operator fun invoke(plant: Plant, currentDate: Date): List<TaskWithState> {
        return tasksRepository.getTasksForPlant(plant)
            .filter { task ->
                currentDate.isDueDate(
                    startDate = plant.creationDate,
                    intervalDays = task.frequency
                )
            }
            .map { task ->
                TaskWithState(
                    task = task,
                    isCompleted = tasksHistoryRepository.isTaskCompletedForDate(task, currentDate)
                )
            }
    }
}
