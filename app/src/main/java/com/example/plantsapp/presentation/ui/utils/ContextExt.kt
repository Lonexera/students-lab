package com.example.plantsapp.presentation.ui.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.example.plantsapp.BuildConfig
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

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

fun Context.saveImageInAppStorage(imageUri: Uri): Uri? {
    val newFile = try {
        val file = createImageFile(this)

        val inputStream = contentResolver.openInputStream(imageUri)
        val outputStream = FileOutputStream(file)

        copyStream(inputStream!!, outputStream)

        outputStream.close()
        inputStream.close()

        file
    } catch (exception: IOException) {
        Timber.e(exception)
        null
    }

    return newFile?.let { getFileUri(it) }
}

private fun Context.getFileUri(file: File): Uri {
    return FileProvider.getUriForFile(
        this,
        BuildConfig.APPLICATION_ID,
        file
    )
}

@Throws(IOException::class)
private fun createImageFile(context: Context): File? {

    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    return File.createTempFile(
        TITLE_PLANT_PHOTO,
        ".jpg",
        storageDir
    )
}

@Throws(IOException::class)
private fun copyStream(input: InputStream, output: FileOutputStream) {
    val buffer = ByteArray(BYTE_ARRAY_SIZE)
    var bytesRead: Int
    while (input.read(buffer).also { bytesRead = it } != -1) {
        output.write(buffer, 0, bytesRead)
    }
}

private const val BYTE_ARRAY_SIZE = 1024
