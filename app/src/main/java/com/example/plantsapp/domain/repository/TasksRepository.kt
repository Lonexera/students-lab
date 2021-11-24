package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task

interface TasksRepository {
    suspend fun addTasks(plant: Plant, tasks: List<Task>)
    suspend fun getTasksForPlant(plant: Plant): List<Task>
}
