package com.example.plantsapp.data.firebase.entity

import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskKeys
import java.util.Date

data class FirebaseTask(
    val taskKey: String = "",
    val frequency: Int = 0,
    val lastUpdateDate: Date = Date()
) {
    fun toTask(): Task {
        // TODO remove id from Task after implementing TaskHistoryRepo
        return TaskKeys.getFromKey(taskKey).toTask(
            taskId = 0,
            frequency = frequency,
            lastUpdateDate = lastUpdateDate
        )
    }

    companion object {
        fun from(task: Task): FirebaseTask {
            return FirebaseTask(
                taskKey = TaskKeys.from(task).key,
                frequency = task.frequency,
                lastUpdateDate = task.lastUpdateDate
            )
        }
    }
}
