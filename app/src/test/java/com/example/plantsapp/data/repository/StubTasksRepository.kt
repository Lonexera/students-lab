package com.example.plantsapp.data.repository

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksRepository

class StubTasksRepository(
    private val tasksList: List<Task>
) : TasksRepository {

    override suspend fun addTasks(plant: Plant, tasks: List<Task>) {
        TODO("Stub dao does not implement insert method")
    }

    override suspend fun getTasksForPlant(plant: Plant): List<Task> {
        return tasksList
    }
}
