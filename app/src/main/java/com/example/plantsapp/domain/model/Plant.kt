package com.example.plantsapp.domain.model

data class Plant(
    val name: PlantName,
    val speciesName: String,
    val plantPicture: String,
    val wateringFrequencyDays: Int
) {
    @JvmInline
    value class PlantName(val value: String)
}
