package com.example.plantsapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskKeys

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
    val frequency: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun toTask(): Task {
        return TaskKeys.getFromKey(taskKey).toTask(id, frequency)
    }

    companion object {
        fun from(plant: Plant, task: Task): RoomTask {
            return RoomTask(
                TaskKeys.from(task).key,
                plant.name.value,
                task.frequency
            )
        }
    }
}
