package com.example.plantsapp.data.room.repository

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import java.util.Date

class StubTasksHistoryRepository(
    private val areTasksCompleted: Boolean
) : TasksHistoryRepository {

    override suspend fun createTaskCompletion(plant: Plant, task: Task, completionDate: Date) {
        TODO("Stub dao does not implement insert method")
    }

    override suspend fun isTaskCompletedForDate(plant: Plant, task: Task, date: Date): Boolean {
        return areTasksCompleted
    }

    override suspend fun getTaskCompletionDates(plant: Plant, task: Task): List<Date> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTaskHistory(plant: Plant, task: Task) {
        TODO("Not yet implemented")
    }
}
