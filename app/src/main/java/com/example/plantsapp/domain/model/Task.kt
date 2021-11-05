package com.example.plantsapp.domain.model

import android.net.Uri
import com.example.plantsapp.R

// TODO refactor Task class
sealed class Task {
    abstract val plantName: String
    abstract val plantPicture: Uri?
    abstract val frequencyInDays: Int
    abstract val taskAction: Int
    abstract val taskIcon: Int

    data class WateringTask(
        override val plantName: String,
        override val plantPicture: Uri?,
        override val frequencyInDays: Int
    ) : Task() {
        override val taskAction: Int = R.string.title_watering_task
        override val taskIcon: Int = R.drawable.ic_watering
    }

    data class SprayingTask(
        override val plantName: String,
        override val plantPicture: Uri?,
        override val frequencyInDays: Int
    ) : Task() {
        override val taskAction: Int = R.string.title_spraying_task
        override val taskIcon: Int = R.drawable.ic_spraying
    }

    data class LooseningTask(
        override val plantName: String,
        override val plantPicture: Uri?,
        override val frequencyInDays: Int
    ) : Task() {
        override val taskAction: Int = R.string.title_loosening_task
        override val taskIcon: Int = R.drawable.ic_watering
    }
}
