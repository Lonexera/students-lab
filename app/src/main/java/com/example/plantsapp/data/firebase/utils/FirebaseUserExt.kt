package com.example.plantsapp.data.firebase.utils

import com.example.plantsapp.domain.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toUser(): User {
    // TODO handle situation when user has no displayName and remove !!
    return User(
        uid = uid,
        name = displayName!!,
        profilePicture = photoUrl
    )
}
