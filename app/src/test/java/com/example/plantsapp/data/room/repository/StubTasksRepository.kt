package com.example.plantsapp.data.room.repository

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksRepository
import java.util.Date

class StubTasksRepository(
    private val tasksList: List<Task>
) : TasksRepository {

    override suspend fun addTasks(plant: Plant, tasks: List<Task>) {
        TODO("Stub dao does not implement insert method")
    }

    override suspend fun getTasksForPlant(plant: Plant): List<Task> {
        return tasksList
    }

    override suspend fun updateTask(plant: Plant, task: Task, completionDate: Date) {
        TODO("Stub dao does not implement update method")
    }
}
