package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import java.util.Date

interface TasksRepository {
    suspend fun addTasks(plant: Plant, tasks: List<Task>)
    suspend fun getTasksForPlant(plant: Plant): List<Task>
    suspend fun updateTask(plant: Plant, task: Task, completionDate: Date)
    suspend fun deleteAllTasks(plant: Plant)
}
