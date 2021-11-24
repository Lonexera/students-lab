package com.example.plantsapp.domain.model

sealed class Task {
    abstract val frequency: Int
    abstract val id: Int

    data class WateringTask(
        override val frequency: Int,
        override val id: Int = 0
    ) : Task()

    data class SprayingTask(
        override val frequency: Int,
        override val id: Int = 0
    ) : Task()

    data class LooseningTask(
        override val frequency: Int,
        override val id: Int = 0
    ) : Task()
}
