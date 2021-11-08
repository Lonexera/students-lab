package com.example.plantsapp.domain.model

data class PlantWithTasks(
    val plant: Plant,
    val tasks: List<Task>
)
