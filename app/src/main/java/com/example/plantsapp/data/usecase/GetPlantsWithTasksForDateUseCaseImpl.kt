package com.example.plantsapp.data.usecase

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskWithState
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantsapp.domain.repository.TasksRepository
import com.example.plantsapp.domain.usecase.GetPlantsWithTasksForDateUseCase
import com.example.plantsapp.presentation.ui.utils.atStartDay
import kotlinx.coroutines.flow.first
import java.util.Date
import javax.inject.Inject

class GetPlantsWithTasksForDateUseCaseImpl @Inject constructor(
    private val plantsRepository: PlantsRepository,
    private val tasksRepository: TasksRepository,
    private val tasksHistoryRepository: TasksHistoryRepository
) : GetPlantsWithTasksForDateUseCase {

    override suspend fun invoke(date: Date): List<Pair<Plant, List<TaskWithState>>> {
        return plantsRepository
            .fetchPlants()
            .first()
            .map { plant ->
                plant to getFittingTasksForDate(
                    tasks = tasksRepository.getTasksForPlant(plant),
                    currentDate = date
                )
                    .map {
                        TaskWithState(
                            task = it,
                            isCompleted = tasksHistoryRepository.isTaskCompletedForDate(it, date)
                        )
                    }
            }
            .filter { it.second.isNotEmpty() }
    }

    private fun getFittingTasksForDate(
        tasks: List<Task>,
        currentDate: Date
    ): List<Task> {
        return tasks.filter { task ->
            checkIfDateIsRepeatedWithInterval(
                currentDate,
                task.creationDate,
                task.frequency
            )
        }
    }

    private fun checkIfDateIsRepeatedWithInterval(
        date: Date,
        startDate: Date,
        intervalDays: Int
    ): Boolean {
        return ((date.atStartDay().time - startDate.atStartDay().time) / DAY_IN_MILLISECONDS) %
                intervalDays == 0L
    }

    companion object {
        private const val DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24
    }
}
