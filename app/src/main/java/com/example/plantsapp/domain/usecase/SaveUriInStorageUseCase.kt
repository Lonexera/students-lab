package com.example.plantsapp.domain.usecase

import android.net.Uri
import java.io.IOException

interface SaveUriInStorageUseCase {

    @Throws(IOException::class)
    fun saveImage(uri: Uri): Uri
}
