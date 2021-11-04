package com.example.plantsapp.domain.model

import android.net.Uri

// TODO refactor Task class
data class Task(
    val taskIcon: Int,
    val taskAction: Int,
    val plantName: String,
    val plantPicture: Uri?,
    val frequencyInDays: Int
)
