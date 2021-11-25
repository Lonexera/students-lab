package com.example.plantsapp.domain.usecase

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.TaskWithState
import java.util.Date

interface GetTasksForPlantForDateUseCase {
    suspend operator fun invoke(plant: Plant, currentDate: Date): List<TaskWithState>
}
