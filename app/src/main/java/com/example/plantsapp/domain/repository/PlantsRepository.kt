package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantsRepository {

    fun observePlants(): Flow<List<Plant>>
    suspend fun fetchPlants(): List<Plant>
    suspend fun addPlant(plant: Plant)
    suspend fun getPlantByName(name: Plant.Name): Plant
    suspend fun deletePlant(plant: Plant)
}
