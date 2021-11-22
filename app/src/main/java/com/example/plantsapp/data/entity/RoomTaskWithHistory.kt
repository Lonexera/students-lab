package com.example.plantsapp.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RoomTaskWithHistory(
    @Embedded val task: RoomTask,
    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val taskHistory: List<RoomTaskCompletion>
)
