package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.PlantWithTasks
import com.example.plantsapp.domain.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface TasksRepository {

    suspend fun addTasks(plant: Plant, tasks: List<Task>)
    suspend fun getPlantsWithTasksForDate(date: Date): Flow<List<PlantWithTasks>>
}
