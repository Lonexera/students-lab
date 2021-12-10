package com.example.plantsapp.domain.model

import android.net.Uri

data class User(
    val uid: String,
    val name: String,
    val profilePicture: Uri?
)
