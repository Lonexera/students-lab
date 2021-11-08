package com.example.plantsapp.domain.model

sealed class Task {
    abstract val frequencyInDays: Int

    data class WateringTask(
        override val frequencyInDays: Int
    ) : Task()

    data class SprayingTask(
        override val frequencyInDays: Int
    ) : Task()

    data class LooseningTask(
        override val frequencyInDays: Int
    ) : Task()
}
