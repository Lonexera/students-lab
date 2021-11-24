package com.example.plantsapp.domain.model

import android.net.Uri
import java.util.Date

data class Plant(
    val name: Name,
    val speciesName: String,
    val plantPicture: Uri?,
    val creationDate: Date = Date()
) {
    @JvmInline
    value class Name(val value: String)
}
