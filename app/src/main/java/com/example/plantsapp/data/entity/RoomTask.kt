package com.example.plantsapp.data.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task

@Entity(tableName = "tasks")
data class RoomTask(
    val taskKey: String,
    val plantName: String,
    val frequency: Int
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0

    // TODO remove plantPicture from task
    fun toTask(plantPicture: Uri?): Task {
        return when (TaskKeys.getFromKey(taskKey)) {
            TaskKeys.WATERING_TASK -> Task.WateringTask(plantName, plantPicture, frequency)
            TaskKeys.SPRAYING_TASK -> Task.SprayingTask(plantName, plantPicture, frequency)
            TaskKeys.LOOSENING_TASK -> Task.LooseningTask(plantName, plantPicture, frequency)
            else -> throw NoSuchElementException("Undefined Task Type!")
        }
    }

    companion object {
        fun from(plant: Plant, task: Task): RoomTask {
            return RoomTask(
                TaskKeys.from(task).key,
                plant.name.value,
                task.frequencyInDays
            )
        }
    }
}
