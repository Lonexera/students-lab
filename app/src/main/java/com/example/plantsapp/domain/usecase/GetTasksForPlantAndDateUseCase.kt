package com.example.plantsapp.domain.usecase

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import java.util.Date

interface GetTasksForPlantAndDateUseCase {
    suspend operator fun invoke(plant: Plant, currentDate: Date): List<Pair<Task, Boolean>>
}
