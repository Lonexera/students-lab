package com.example.plantsapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

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
}
