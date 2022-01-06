package com.example.plantsapp.domain.usecase

import com.example.plantsapp.domain.model.Plant

interface DeletePlantUseCase {
    suspend operator fun invoke(plant: Plant)
}
