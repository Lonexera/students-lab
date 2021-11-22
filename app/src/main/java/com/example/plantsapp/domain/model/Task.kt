package com.example.plantsapp.domain.model

sealed class Task {
    abstract val frequencyInDays: Int
    abstract val id: Int

    data class WateringTask(
        override val frequencyInDays: Int,
        override val id: Int = 0
    ) : Task()

    data class SprayingTask(
        override val frequencyInDays: Int,
        override val id: Int = 0
    ) : Task()

    data class LooseningTask(
        override val frequencyInDays: Int,
        override val id: Int = 0
    ) : Task()
}
