package com.example.plantsapp.data.usecase

import android.content.Context
import android.net.Uri
import com.example.plantsapp.domain.usecase.SaveUriInStorageUseCase
import com.example.plantsapp.presentation.ui.utils.createImageFile
import com.example.plantsapp.presentation.ui.utils.getFileUri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class SaveUriInStorageUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SaveUriInStorageUseCase {

    @Throws(IOException::class)
    override fun saveImage(uri: Uri): Uri {

        val newFile = createImageFile(context)

        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(newFile)

        copyStream(inputStream!!, outputStream)

        outputStream.close()
        inputStream.close()

        return context.getFileUri(newFile)
    }

    @Throws(IOException::class)
    private fun copyStream(input: InputStream, output: FileOutputStream) {
        val buffer = ByteArray(BYTE_ARRAY_SIZE)
        var bytesRead: Int
        while (input.read(buffer).also { bytesRead = it } != -1) {
            output.write(buffer, 0, bytesRead)
        }
    }

    companion object {
        private const val BYTE_ARRAY_SIZE = 1024
    }
}
