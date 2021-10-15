package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.Plant

interface PlantsRepository {

    fun allPlants(): List<Plant>
    fun addPlant(plant: Plant)
}
