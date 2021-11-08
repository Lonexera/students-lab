package com.example.plantsapp.data.repository

import com.example.plantsapp.data.dao.RoomPlantWithTasksDao
import com.example.plantsapp.data.entity.RoomPlantWithTasks
import com.example.plantsapp.data.entity.RoomTask
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.PlantWithTasks
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class RoomTasksRepository(
    private val plantsWithTasksDao: RoomPlantWithTasksDao
) : TasksRepository {

    override suspend fun addTasks(plant: Plant, tasks: List<Task>) {
        plantsWithTasksDao.insert(
            tasks.map { RoomTask.from(plant, it) }
        )
    }

    override suspend fun getPlantsWithTasksForDate(date: Date): Flow<List<PlantWithTasks>> {
        return plantsWithTasksDao.getPlantsWithTasks()
            .map {
                it.getPlantsWithFittingTasks(date)
            }
    }

    private fun List<RoomPlantWithTasks>.getPlantsWithFittingTasks(date: Date): List<PlantWithTasks> {
        return this.map {
            PlantWithTasks(
                it.plant.toPlant(),
                it.tasks
                    .filter { roomTask ->
                        checkIfDateIsRepeatedWithInterval(
                            date,
                            Date(it.plant.creationDateMillis),
                            roomTask.frequency
                        )
                    }
                    .map { roomTask ->
                        roomTask.toTask()
                    }
            )
        }
            .filter { it.tasks.isNotEmpty() }
    }

    private fun checkIfDateIsRepeatedWithInterval(
        date: Date,
        startDate: Date,
        intervalDays: Int
    ): Boolean {
        return ((date.time - startDate.time) / DAY_IN_MILLISECONDS) %
                intervalDays == 0L
    }
}

private const val DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24
