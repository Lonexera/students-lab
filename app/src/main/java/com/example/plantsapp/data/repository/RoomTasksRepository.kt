package com.example.plantsapp.data.repository

import com.example.plantsapp.data.dao.RoomTasksDao
import com.example.plantsapp.data.entity.RoomTask
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksRepository
import javax.inject.Inject

class RoomTasksRepository @Inject constructor(
    private val plantsWithTasksDao: RoomTasksDao
) : TasksRepository {

    override suspend fun addTasks(plant: Plant, tasks: List<Task>) {
        plantsWithTasksDao.insert(
            tasks.map { RoomTask.from(plant, it) }
        )
    }

    override suspend fun getTasksForPlant(plant: Plant): List<Task> {
        return plantsWithTasksDao
            .getTasksForPlantName(plant.name.value)
            .map { it.toTask() }
    }
}
