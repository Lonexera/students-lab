package com.example.plantsapp.data.repository

import com.example.plantsapp.data.dao.RoomTasksDao
import com.example.plantsapp.data.entity.RoomTask
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksRepository
import java.util.Date
import javax.inject.Inject

class RoomTasksRepository @Inject constructor(
    private val tasksDao: RoomTasksDao
) : TasksRepository {

    override suspend fun addTasks(plant: Plant, tasks: List<Task>) {
        tasksDao.insert(
            tasks.map { RoomTask.from(plant, it) }
        )
    }

    override suspend fun getTasksForPlant(plant: Plant): List<Task> {
        return tasksDao
            .getTasksForPlantName(plant.name.value)
            .map { it.toTask() }
    }

    override suspend fun updateTask(task: Task, completionDate: Date) {
        tasksDao.update(
            taskId = task.id,
            completionDate = completionDate.time
        )
    }
}
