package com.example.plantsapp.domain.model

data class TaskWithState(
    val task: Task,
    val isCompleted: Boolean
)
