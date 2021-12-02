package com.example.plantsapp.data.utils

import com.example.plantsapp.data.room.entity.RoomTask
import com.example.plantsapp.domain.model.TaskKeys
import java.util.Date

internal fun createRoomTask(
    taskKey: TaskKeys = TaskKeys.WATERING_TASK,
    plantName: String = "",
    frequency: Int = 1,
    lastUpdateDate: Date
): RoomTask {
    return RoomTask(
        taskKey = taskKey.key,
        plantName = plantName,
        frequency = frequency,
        lastUpdateDate = lastUpdateDate.time
    )
}
