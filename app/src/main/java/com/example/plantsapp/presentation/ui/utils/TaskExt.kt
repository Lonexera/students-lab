package com.example.plantsapp.presentation.ui.utils

import com.example.plantsapp.R
import com.example.plantsapp.domain.model.Task

fun Task.getTitleRes(): Int {
    return when (this) {
        is Task.WateringTask -> R.string.title_watering_task
        is Task.SprayingTask -> R.string.title_spraying_task
        is Task.LooseningTask -> R.string.title_loosening_task
        is Task.TakingPhotoTask -> R.string.title_taking_photo_task
    }
}

fun Task.getIconRes(): Int {
    return when (this) {
        is Task.WateringTask -> R.drawable.ic_watering
        is Task.SprayingTask -> R.drawable.ic_spraying
        is Task.LooseningTask -> R.drawable.ic_loosening
        is Task.TakingPhotoTask -> R.drawable.ic_taking_photo
    }
}
