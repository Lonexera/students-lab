package com.example.plantsapp.data.usecase

import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantsapp.domain.repository.TasksRepository
import com.example.plantsapp.domain.usecase.CompleteTaskUseCase
import java.util.Date
import javax.inject.Inject

class CompleteTaskUseCaseImpl @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val tasksHistoryRepository: TasksHistoryRepository
) : CompleteTaskUseCase {

    override suspend fun invoke(task: Task, completionDate: Date) {
        tasksHistoryRepository.createTaskCompletion(task, completionDate)
        tasksRepository.updateTask(task, completionDate)
    }
}
