package com.example.plantsapp.domain.model

import java.util.Date

sealed class Task {
    abstract val frequency: Int
    abstract val creationDate: Date
    abstract val id: Int

    data class WateringTask(
        override val frequency: Int,
        override val creationDate: Date = Date(),
        override val id: Int = 0
    ) : Task()

    data class SprayingTask(
        override val frequency: Int,
        override val creationDate: Date = Date(),
        override val id: Int = 0
    ) : Task()

    data class LooseningTask(
        override val frequency: Int,
        override val creationDate: Date = Date(),
        override val id: Int = 0
    ) : Task()
}
