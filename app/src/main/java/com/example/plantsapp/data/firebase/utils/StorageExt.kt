package com.example.plantsapp.data.firebase.utils

import android.net.Uri
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.User
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

suspend fun StorageReference.addImage(user: User, plant: Plant, picture: Uri): Uri {
    val storageImagePath = getPictureStoragePath(
        userUid = user.uid,
        pictureName = picture.lastPathSegment
            ?: throw IllegalArgumentException("Provided picture uri has unsupported name!"),
        plantName = plant.name.value
    )
    return child(storageImagePath)
        .apply {
            putFile(picture)
                .await()
        }
        .downloadUrl
        .await()
}

fun getPictureStoragePath(
    userUid: String,
    pictureName: String,
    plantName: String
): String {
    return buildString {
        append(userUid)
        append("/")
        append(STORAGE_PICTURES_DIR_PATH)
        append(plantName)
        append("/")
        append(pictureName)
    }
}

private const val STORAGE_PICTURES_DIR_PATH = "images/"
