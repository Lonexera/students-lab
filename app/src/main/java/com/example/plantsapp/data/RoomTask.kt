package com.example.plantsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plantsapp.domain.model.Task

@Entity(tableName = "tasks")
data class RoomTask(
    val plantName: String,
    val action: Int,
    val icon: Int,
    val frequency: Int
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    companion object {
        fun from(task: Task): RoomTask {
            return RoomTask(
                task.plantName,
                task.taskAction,
                task.taskIcon,
                task.frequencyInDays
            )
        }
    }
}
