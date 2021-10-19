package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.Plant

interface PlantsRepository {

    suspend fun fetchPlants(): List<Plant>
    suspend fun addPlant(plant: Plant)
    suspend fun getPlantByName(name: Plant.Name): Plant
}
