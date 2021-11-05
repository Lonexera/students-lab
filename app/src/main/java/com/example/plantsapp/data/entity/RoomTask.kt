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

    fun toTask(plantPicture: Uri?): Task {
        return when (TaskKeys.getFromKey(taskKey)) {
            TaskKeys.WATERING_TASK -> Task.WateringTask(plantName, plantPicture, frequency)
            TaskKeys.SPRAYING_TASK -> Task.SprayingTask(plantName, plantPicture, frequency)
            TaskKeys.LOOSENING_TASK -> Task.LooseningTask(plantName, plantPicture, frequency)
            else -> throw NoSuchElementException("Undefined Task Type!")
        }
    }

    companion object {
        fun from(task: Task): RoomTask {
            return RoomTask(
                TaskKeys.from(task).key,
                task.plantName,
                task.frequencyInDays
            )
        }
    }
}
