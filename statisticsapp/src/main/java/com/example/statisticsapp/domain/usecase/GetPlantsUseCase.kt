package com.example.statisticsapp.domain.usecase

import com.example.plantsapp.domain.model.Plant

interface GetPlantsUseCase {
    suspend operator fun invoke(): List<Plant>
}
