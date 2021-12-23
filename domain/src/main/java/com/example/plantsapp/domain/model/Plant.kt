package com.example.plantsapp.domain.model

data class Plant(
    val name: Name,
    val speciesName: String,
    val plantPicture: String?,
) {
    @JvmInline
    value class Name(val value: String)
}
