package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantsRepository {

    suspend fun fetchPlants(): Flow<List<Plant>>
    suspend fun addPlant(plant: Plant)
    suspend fun getPlantByName(name: Plant.Name): Plant
}
