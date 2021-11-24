package com.example.plantsapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import java.util.Date
import kotlin.NoSuchElementException

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            entity = RoomPlant::class,
            parentColumns = ["name"],
            childColumns = ["plantName"]
        )
    ]
)
data class RoomTask(
    val taskKey: String,
    val plantName: String,
    val frequency: Int,
    val creationTime: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun toTask(): Task {
        return when (TaskKeys.getFromKey(taskKey)) {
            TaskKeys.WATERING_TASK -> Task.WateringTask(frequency, Date(creationTime), id)
            TaskKeys.SPRAYING_TASK -> Task.SprayingTask(frequency, Date(creationTime), id)
            TaskKeys.LOOSENING_TASK -> Task.LooseningTask(frequency, Date(creationTime), id)
            else -> throw NoSuchElementException("Undefined Task Type!")
        }
    }

    companion object {
        fun from(plant: Plant, task: Task): RoomTask {
            return RoomTask(
                TaskKeys.from(task).key,
                plant.name.value,
                task.frequency,
                task.creationDate.time
            )
        }
    }
}
