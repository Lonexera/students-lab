package com.example.plantsapp.data.repository

import com.example.plantsapp.data.dao.RoomPlantWithTasksDao
import com.example.plantsapp.data.dao.RoomTaskHistoryDao
import com.example.plantsapp.data.entity.RoomPlant
import com.example.plantsapp.data.entity.RoomPlantWithTasks
import com.example.plantsapp.data.entity.RoomTask
import com.example.plantsapp.data.entity.RoomTaskCompletion
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskWithState
import com.example.plantsapp.domain.repository.TasksRepository
import com.example.plantsapp.presentation.ui.utils.atEndDay
import com.example.plantsapp.presentation.ui.utils.atStartDay
import java.util.Date
import javax.inject.Inject

class RoomTasksRepository @Inject constructor(
    private val plantsWithTasksDao: RoomPlantWithTasksDao,
    private val taskHistoryDao: RoomTaskHistoryDao
) : TasksRepository {

    override suspend fun addTasks(plant: Plant, tasks: List<Task>) {
        plantsWithTasksDao.insert(
            tasks.map { RoomTask.from(plant, it) }
        )
    }

    override suspend fun getPlantsWithTasksForDate(date: Date): List<Pair<Plant, List<TaskWithState>>> {
        return plantsWithTasksDao
            .getPlantsWithTasks()
            .getPlantsWithFittingTasks(date)
    }

    override suspend fun completeTask(task: Task, completionDate: Date) {
        taskHistoryDao.addTaskCompletionDate(
            RoomTaskCompletion(
                taskId = task.id,
                completionDate = completionDate.time
            )
        )
    }

    // TODO refactor this later
    private suspend fun List<RoomPlantWithTasks>.getPlantsWithFittingTasks(date: Date):
            List<Pair<Plant, List<TaskWithState>>> {
        return this.map {
            it.plant.toPlant() to
                    getFittingTasksForPlant(it.plant, it.tasks, date)
                        .map {
                            TaskWithState(
                                task = it.toTask(),
                                isCompleted = taskHistoryDao.isTaskCompletedForDate(
                                    taskId = it.id,
                                    startDay = date.atStartDay().time,
                                    endDay = date.atEndDay().time
                                )
                            )
                        }
        }
            .filter { it.second.isNotEmpty() }
    }

    private fun getFittingTasksForPlant(
        plant: RoomPlant,
        tasks: List<RoomTask>,
        currentDate: Date
    ): List<RoomTask> {
        return tasks.filter { roomTask ->
            checkIfDateIsRepeatedWithInterval(
                currentDate,
                Date(plant.creationDateMillis),
                roomTask.frequency
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
}

private const val DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24
