package com.example.plantsapp.domain.model

import android.net.Uri

sealed class Task {
    abstract val plantName: String
    abstract val plantPicture: Uri?
    abstract val frequencyInDays: Int

    data class WateringTask(
        override val plantName: String,
        override val plantPicture: Uri?,
        override val frequencyInDays: Int
    ) : Task()

    data class SprayingTask(
        override val plantName: String,
        override val plantPicture: Uri?,
        override val frequencyInDays: Int
    ) : Task()

    data class LooseningTask(
        override val plantName: String,
        override val plantPicture: Uri?,
        override val frequencyInDays: Int
    ) : Task()
}
