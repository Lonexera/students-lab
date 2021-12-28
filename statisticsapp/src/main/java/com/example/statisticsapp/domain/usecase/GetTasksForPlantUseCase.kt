package com.example.statisticsapp.domain.usecase

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task

interface GetTasksForPlantUseCase {
    suspend operator fun invoke(plant: Plant): List<Task>
}
