package com.example.plantsapp.domain.usecase

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.TaskWithState
import java.util.Date

interface GetPlantsWithTasksForDateUseCase {
    suspend operator fun invoke(date: Date): List<Pair<Plant, List<TaskWithState>>>
}
