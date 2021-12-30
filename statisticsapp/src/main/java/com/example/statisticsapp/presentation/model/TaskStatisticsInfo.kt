package com.example.statisticsapp.presentation.model

import com.example.plantsapp.domain.model.Task

data class TaskStatisticsInfo(
    val task: Task,
    val amount: Int
)
