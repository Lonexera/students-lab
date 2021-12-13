package com.example.plantsapp.data.firebase.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.core.net.toUri
import com.example.plantsapp.domain.model.User

var SharedPreferences.user: User?
    get() {
        val userUid = getString(PREFERENCES_USER_UID, null)
        val userName = getString(PREFERENCES_USER_NAME, null)
        val userPicture = getString(PREFERENCES_USER_PICTURE, null)

        return if (userUid == null || userName == null) {
            null
        } else {
            User(
                uid = userUid,
                name = userName,
                profilePicture = userPicture?.toUri()
            )
        }
    }
    set(value) = edit(commit = true) {
        if (value == null) {
            remove(PREFERENCES_USER_UID)
            remove(PREFERENCES_USER_NAME)
            remove(PREFERENCES_USER_PICTURE)
        } else {
            putString(PREFERENCES_USER_UID, value.uid)
            putString(PREFERENCES_USER_NAME, value.name)
            putString(PREFERENCES_USER_PICTURE, value.profilePicture?.toString())
        }
    }

private const val PREFERENCES_USER_NAME = "PREFERENCES_USER_NAME"
private const val PREFERENCES_USER_UID = "PREFERENCES_USER_UID"
private const val PREFERENCES_USER_PICTURE = "PREFERENCES_USER_PICTURE"
