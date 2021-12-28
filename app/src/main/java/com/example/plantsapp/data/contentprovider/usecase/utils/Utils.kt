package com.example.plantsapp.data.contentprovider.usecase.utils

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskKeys
import java.util.Date

fun getDefaultPlantWithName(plantName: String): Plant {
    return Plant(
        name = Plant.Name(plantName),
        speciesName = "",
        plantPicture = null
    )
}

fun getDefaultTaskWithKey(taskKey: String): Task {
    return TaskKeys.getFromKey(taskKey)
        .toTask(
            taskId = 0, // TODO remove id from Task model
            frequency = 0,
            lastUpdateDate = Date()
        )
}
