package com.example.plantsapp.domain.model

import java.util.Date
import kotlin.NoSuchElementException

enum class TaskKeys(val key: String) {
    WATERING_TASK("KEY_WATERING_TASK"),
    SPRAYING_TASK("KEY_SPRAYING_TASK"),
    LOOSENING_TASK("KEY_LOOSENING_TASK"),
    TAKING_PHOTO_TASK("KEY_TAKING_PHOTO_TASK");

    fun toTask(taskId: Int, frequency: Int, lastUpdateDate: Date): Task {
        return when (getFromKey(key)) {
            WATERING_TASK -> Task.WateringTask(frequency, lastUpdateDate, taskId)
            SPRAYING_TASK -> Task.SprayingTask(frequency, lastUpdateDate, taskId)
            LOOSENING_TASK -> Task.LooseningTask(frequency, lastUpdateDate, taskId)
            TAKING_PHOTO_TASK -> Task.TakingPhotoTask(frequency, lastUpdateDate, taskId)
        }
    }

    companion object {
        fun from(task: Task): TaskKeys {
            return when (task) {
                is Task.WateringTask -> WATERING_TASK
                is Task.SprayingTask -> SPRAYING_TASK
                is Task.LooseningTask -> LOOSENING_TASK
                is Task.TakingPhotoTask -> TAKING_PHOTO_TASK
            }
        }

        fun getFromKey(key: String): TaskKeys =
            values().find { it.key == key } ?: throw NoSuchElementException("Undefined Task Type!")
    }
}
