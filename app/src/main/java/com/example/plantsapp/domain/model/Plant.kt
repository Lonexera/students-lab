package com.example.plantsapp.domain.model

data class Plant(
    val name: Name,
    val speciesName: String,
    val plantPicture: String,
    val wateringFrequencyDays: Int
) {
    @JvmInline
    value class Name(val value: String)
}
