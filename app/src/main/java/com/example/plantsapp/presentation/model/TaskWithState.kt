package com.example.plantsapp.presentation.model

import com.example.plantsapp.domain.model.Task

data class TaskWithState(
    val task: Task,
    val isCompleted: Boolean,
    val isCompletable: Boolean
)
