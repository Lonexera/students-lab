package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface TasksRepository {

    suspend fun addTasks(tasks: List<Task>)
    suspend fun getTasksForDate(date: Date): Flow<List<Task>>
}
