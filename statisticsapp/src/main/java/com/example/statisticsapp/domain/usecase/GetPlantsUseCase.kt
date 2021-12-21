package com.example.statisticsapp.domain.usecase

import com.example.statisticsapp.domain.model.Plant

interface GetPlantsUseCase {
    suspend operator fun invoke(): List<Plant>
}
