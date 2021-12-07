package com.example.plantsapp.domain.usecase

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import java.util.Date

interface CompleteTaskUseCase {
    suspend operator fun invoke(plant: Plant, task: Task, completionDate: Date)
}
