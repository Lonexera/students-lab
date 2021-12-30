package com.example.statisticsapp.presentation.model

import com.example.plantsapp.domain.model.Plant

data class PlantStatisticsInfo(
    val plant: Plant,
    val tasks: List<TaskStatisticsInfo>
)
