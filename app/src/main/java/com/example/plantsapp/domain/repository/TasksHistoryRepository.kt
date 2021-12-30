package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import java.util.Date

interface TasksHistoryRepository {

    suspend fun createTaskCompletion(plant: Plant, task: Task, completionDate: Date)
    suspend fun isTaskCompletedForDate(plant: Plant, task: Task, date: Date): Boolean
    suspend fun getTaskCompletionDates(plant: Plant, task: Task): List<Date>
}
