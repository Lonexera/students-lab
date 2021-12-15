package com.example.plantsapp.data.firebase.entity

import android.net.Uri
import java.util.Date

data class FirebasePlantPhoto(
    val photoUrl: String = "",
    val creationDate: Date = Date()
) {
    companion object {
        fun from(photoUri: Uri, creationDate: Date): FirebasePlantPhoto {
            return FirebasePlantPhoto(
                photoUrl = photoUri.toString(),
                creationDate = creationDate
            )
        }
    }
}
