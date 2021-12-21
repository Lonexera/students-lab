package com.example.statisticsapp.domain.model

import android.net.Uri

data class Plant(
    val name: Name,
    val speciesName: String,
    val plantPicture: Uri?,
) {
    @JvmInline
    value class Name(val value: String)
}
