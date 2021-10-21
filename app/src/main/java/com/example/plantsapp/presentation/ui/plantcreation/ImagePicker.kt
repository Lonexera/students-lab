package com.example.plantsapp.presentation.ui.plantcreation

import android.net.Uri
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts

class ImagePicker(
    private val activityResultRegistry: ActivityResultRegistry,
    private val callback: (imageUri: Uri?) -> Unit
) {
    private val getContent = activityResultRegistry.register(
        REGISTER_KEY,
        ActivityResultContracts.GetContent(),
        callback
    )

    fun pickImage() {
        getContent.launch(MIMETYPE_IMAGE)
    }

    companion object {
        private const val REGISTER_KEY = "Image Picker"
        private const val MIMETYPE_IMAGE = "image/*"
    }
}
