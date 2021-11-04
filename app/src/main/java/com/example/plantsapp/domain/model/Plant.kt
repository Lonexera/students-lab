package com.example.plantsapp.domain.model

import android.net.Uri

data class Plant(
    val name: Name,
    val speciesName: String,
    val plantPicture: Uri?,
    val wateringFrequencyDays: Int,
    val sprayingFrequency: Int,
    val looseningFrequency: Int
) {
    @JvmInline
    value class Name(val value: String)
}
