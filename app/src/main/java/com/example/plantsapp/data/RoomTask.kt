package com.example.plantsapp.data

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plantsapp.data.utils.KEY_LOOSENING_TASK
import com.example.plantsapp.data.utils.KEY_SPRAYING_TASK
import com.example.plantsapp.data.utils.KEY_WATERING_TASK
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
