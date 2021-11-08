package com.example.plantsapp.data.repository

import com.example.plantsapp.data.dao.RoomPlantWithTasksDao
import com.example.plantsapp.data.entity.RoomPlantWithTasks
import com.example.plantsapp.data.entity.RoomTask
import com.example.plantsapp.domain.model.Plant
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

    override suspend fun getTasksForDate(date: Date): Flow<List<Task>> {
        return plantsWithTasksDao.getPlantsWithTasks()
            .map {
                it.getAllFittingTasks(date)
            }
    }

    private fun List<RoomPlantWithTasks>.getAllFittingTasks(date: Date): List<Task> {
        return this.flatMap {
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
        }
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
