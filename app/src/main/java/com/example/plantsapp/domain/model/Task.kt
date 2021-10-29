package com.example.plantsapp.domain.model

import android.net.Uri

data class Task(
    val taskAction: String,
    val plantName: String,
    val plantPicture: Uri?
)
