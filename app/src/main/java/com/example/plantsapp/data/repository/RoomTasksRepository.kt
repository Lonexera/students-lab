package com.example.plantsapp.data.repository

import com.example.plantsapp.data.dao.RoomPlantWithTasksDao
import com.example.plantsapp.data.entity.RoomPlant
import com.example.plantsapp.data.entity.RoomPlantWithTasks
import com.example.plantsapp.data.entity.RoomTask
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class RoomTasksRepository @Inject constructor(
    private val plantsWithTasksDao: RoomPlantWithTasksDao
) : TasksRepository {

    override suspend fun addTasks(plant: Plant, tasks: List<Task>) {
        plantsWithTasksDao.insert(
            tasks.map { RoomTask.from(plant, it) }
        )
    }

    override suspend fun getPlantsWithTasksForDate(date: Date): Flow<List<Pair<Plant, List<Task>>>> {
        return plantsWithTasksDao.getPlantsWithTasks()
            .map {
                it.getPlantsWithFittingTasks(date)
            }
    }

    private fun List<RoomPlantWithTasks>.getPlantsWithFittingTasks(date: Date): List<Pair<Plant, List<Task>>> {
        return this.map {
            it.plant.toPlant() to
                    getFittingTasksForPlant(it.plant, it.tasks, date)
                        .map { it.toTask() }
        }
            .filter { it.second.isNotEmpty() }
    }

    private fun checkIfDateIsRepeatedWithInterval(
        date: Date,
        startDate: Date,
        intervalDays: Int
    ): Boolean {
        return ((date.time - startDate.time) / DAY_IN_MILLISECONDS) %
                intervalDays == 0L
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
}

private const val DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24
