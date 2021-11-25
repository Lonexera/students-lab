package com.example.plantsapp.data.repository

import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import java.util.Date

class StubTasksHistoryRepository(
    private val areTasksCompleted: Boolean
) : TasksHistoryRepository {

    override suspend fun createTaskCompletion(task: Task, completionDate: Date) {
        TODO("Stub dao does not implement insert method")
    }

    override suspend fun isTaskCompletedForDate(task: Task, date: Date): Boolean {
        return areTasksCompleted
    }
}
