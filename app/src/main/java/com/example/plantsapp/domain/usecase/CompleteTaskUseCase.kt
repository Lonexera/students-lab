package com.example.plantsapp.domain.usecase

import com.example.plantsapp.domain.model.Task
import java.util.Date

interface CompleteTaskUseCase {
    suspend operator fun invoke(task: Task, completionDate: Date)
}
