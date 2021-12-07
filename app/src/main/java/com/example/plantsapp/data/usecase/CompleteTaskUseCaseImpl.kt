package com.example.plantsapp.data.usecase

import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantsapp.domain.repository.TasksRepository
import com.example.plantsapp.domain.usecase.CompleteTaskUseCase
import java.util.Date
import javax.inject.Inject

class CompleteTaskUseCaseImpl @Inject constructor(
    @FirebaseQualifier private val tasksRepository: TasksRepository,
    @FirebaseQualifier private val tasksHistoryRepository: TasksHistoryRepository
) : CompleteTaskUseCase {

    override suspend fun invoke(plant: Plant, task: Task, completionDate: Date) {
        tasksHistoryRepository.createTaskCompletion(plant, task, completionDate)
        tasksRepository.updateTask(plant, task, completionDate)
    }
}
