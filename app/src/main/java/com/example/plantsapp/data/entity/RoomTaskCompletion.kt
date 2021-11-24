package com.example.plantsapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.plantsapp.domain.model.Task

@Entity(
    tableName = "taskHistory",
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            entity = RoomTask::class,
            parentColumns = ["id"],
            childColumns = ["taskId"]
        )
    ]
)
data class RoomTaskCompletion(
    val taskId: Int,
    val completionDate: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        fun fromTask(task: Task, completionDate: Long): RoomTaskCompletion {
            return RoomTaskCompletion(
                taskId = task.id,
                completionDate = completionDate
            )
        }
    }
}
