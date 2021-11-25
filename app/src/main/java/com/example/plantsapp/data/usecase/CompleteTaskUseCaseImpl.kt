package com.example.plantsapp.data.usecase

import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantsapp.domain.usecase.CompleteTaskUseCase
import java.util.Date
import javax.inject.Inject

class CompleteTaskUseCaseImpl @Inject constructor(
    private val repository: TasksHistoryRepository
) : CompleteTaskUseCase {

    override suspend fun invoke(task: Task, completionDate: Date) {
        repository.createTaskCompletion(task, completionDate)
    }
}
