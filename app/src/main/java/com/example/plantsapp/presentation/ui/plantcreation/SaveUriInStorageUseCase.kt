package com.example.plantsapp.presentation.ui.plantcreation

import android.content.Context
import android.net.Uri
import com.example.plantsapp.presentation.ui.utils.createImageFile
import com.example.plantsapp.presentation.ui.utils.getFileUri
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class SaveUriInStorageUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun saveImage(uri: Uri): SavingUriOutput {
        val file = try {
            val newFile = createImageFile(context) ?: throw IOException()

            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(newFile)

            copyStream(inputStream!!, outputStream)

            outputStream.close()
            inputStream.close()

            newFile
        } catch (exception: IOException) {
            Timber.e(exception)
            return SavingUriOutput.Failure
        }

        return SavingUriOutput.Success(context.getFileUri(file))
    }

    @Throws(IOException::class)
    private fun copyStream(input: InputStream, output: FileOutputStream) {
        val buffer = ByteArray(BYTE_ARRAY_SIZE)
        var bytesRead: Int
        while (input.read(buffer).also { bytesRead = it } != -1) {
            output.write(buffer, 0, bytesRead)
        }
    }

    sealed class SavingUriOutput {
        data class Success(val uri: Uri) : SavingUriOutput()
        object Failure : SavingUriOutput()
    }

    companion object {
        private const val BYTE_ARRAY_SIZE = 1024
    }
}
