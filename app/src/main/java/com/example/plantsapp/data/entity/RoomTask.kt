package com.example.plantsapp.data.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plantsapp.domain.model.Task

@Entity(tableName = "tasks")
data class RoomTask(
    @ColumnInfo(name = "task_key") val taskKey: String,
    @ColumnInfo(name = "plant_name") val plantName: String,
    @ColumnInfo(name = "frequency") val frequency: Int
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0

    companion object {
        fun from(task: Task): RoomTask {
            val taskKey = when (task) {
                is Task.WateringTask -> KEY_WATERING_TASK
                is Task.SprayingTask -> KEY_SPRAYING_TASK
                is Task.LooseningTask -> KEY_LOOSENING_TASK
            }

            return RoomTask(
                taskKey,
                task.plantName,
                task.frequencyInDays
            )
        }

        private const val KEY_WATERING_TASK = "KEY_WATERING_TASK"
        private const val KEY_SPRAYING_TASK = "KEY_SPRAYING_TASK"
        private const val KEY_LOOSENING_TASK = "KEY_LOOSENING_TASK"
    }

    fun toTask(plantPicture: Uri?): Task {
        return when (taskKey) {
            KEY_WATERING_TASK -> Task.WateringTask(plantName, plantPicture, frequency)
            KEY_SPRAYING_TASK -> Task.SprayingTask(plantName, plantPicture, frequency)
            KEY_LOOSENING_TASK -> Task.LooseningTask(plantName, plantPicture, frequency)
            else -> throw NoSuchElementException("Undefined Task Type!")
        }
    }
}
