package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskWithState
import java.util.Date

interface TasksRepository {

    suspend fun addTasks(plant: Plant, tasks: List<Task>)
    suspend fun getPlantsWithTasksForDate(date: Date): List<Pair<Plant, List<TaskWithState>>>
    suspend fun completeTask(task: Task, completionDate: Date)
}
