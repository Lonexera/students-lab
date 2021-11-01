package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface PlantsRepository {

    suspend fun fetchPlants(): Flow<List<Plant>>
    suspend fun addPlant(plant: Plant)
    suspend fun getPlantByName(name: Plant.Name): Plant
    suspend fun getTasksForDate(date: Date): Flow<List<Task>>
}
