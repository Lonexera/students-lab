package com.example.plantsapp.data.entity

import com.example.plantsapp.domain.model.Task

enum class TaskKeys(val key: String) {
    WATERING_TASK("KEY_WATERING_TASK"),
    SPRAYING_TASK("KEY_SPRAYING_TASK"),
    LOOSENING_TASK("KEY_LOOSENING_TASK");

    companion object {
        fun from(task: Task): TaskKeys {
            return when (task) {
                is Task.WateringTask -> WATERING_TASK
                is Task.SprayingTask -> SPRAYING_TASK
                is Task.LooseningTask -> LOOSENING_TASK
            }
        }

        fun getFromKey(key: String): TaskKeys? = values().find { it.key == key }
    }
}