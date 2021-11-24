package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.Task
import java.util.Date

interface TasksHistoryRepository {

    suspend fun createTaskCompletion(task: Task, completionDate: Date)
    suspend fun isTaskCompletedForDate(task: Task, date: Date): Boolean
}
