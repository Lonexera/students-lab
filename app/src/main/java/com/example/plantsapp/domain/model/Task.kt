package com.example.plantsapp.domain.model

import java.util.Date

sealed class Task {
    abstract val frequency: Int
    abstract val id: Int
    abstract val lastUpdateDate: Date

    data class WateringTask(
        override val frequency: Int,
        override val lastUpdateDate: Date,
        override val id: Int = 0
    ) : Task()

    data class SprayingTask(
        override val frequency: Int,
        override val lastUpdateDate: Date,
        override val id: Int = 0
    ) : Task()

    data class LooseningTask(
        override val frequency: Int,
        override val lastUpdateDate: Date,
        override val id: Int = 0
    ) : Task()

    data class TakingPhotoTask(
        override val frequency: Int,
        override val lastUpdateDate: Date,
        override val id: Int = 0
    ) : Task()
}
