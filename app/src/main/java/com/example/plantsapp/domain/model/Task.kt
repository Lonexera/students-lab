package com.example.plantsapp.domain.model

import android.net.Uri

// TODO refactor Task class
data class Task(
    val taskAction: String,
    val plantName: String,
    val plantPicture: Uri?
)
