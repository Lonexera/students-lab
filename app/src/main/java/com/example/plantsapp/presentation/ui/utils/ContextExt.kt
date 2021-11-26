package com.example.plantsapp.presentation.ui.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.example.plantsapp.BuildConfig
import com.example.plantsapp.R
import timber.log.Timber
import java.io.File
import java.io.IOException

private const val TITLE_PLANT_PHOTO = "plant_photo"

fun Context.getCameraImageOutputUri(): Uri? {

    val photoFile: File? = try {
        createImageFile(this)
    } catch (exception: IOException) {
        Timber.e(exception)
        null
    }

    return photoFile?.let { getFileUri(it) }
}

fun Context.getFileUri(file: File): Uri {
    return FileProvider.getUriForFile(
        this,
        BuildConfig.APPLICATION_ID,
        file
    )
}

// TODO Maybe move to data package
@Throws(IOException::class)
fun createImageFile(context: Context): File {

    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    return File.createTempFile(
        TITLE_PLANT_PHOTO,
        ".jpg",
        storageDir
    )
}

fun Context.getSharedImageTransitionName(uniqueText: String): String {
    return this.getString(
        R.string.title_shared_plant_picture,
        uniqueText
    )
}

fun Context.getSharedPlantNameTransitionName(uniqueText: String): String {
    return this.getString(
        R.string.title_shared_plant_name,
        uniqueText
    )
}
