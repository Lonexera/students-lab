package com.example.plantsapp.data.room.repository

import com.example.plantsapp.data.room.dao.RoomTaskHistoryDao
import com.example.plantsapp.data.room.entity.RoomTaskCompletion
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantsapp.presentation.ui.utils.atEndDay
import com.example.plantsapp.presentation.ui.utils.atStartDay
import java.util.Date
import javax.inject.Inject

@Deprecated("Room will no longer be used in app")
class RoomTasksHistoryRepository @Inject constructor(
    private val taskHistoryDao: RoomTaskHistoryDao
) : TasksHistoryRepository {

    override suspend fun createTaskCompletion(plant: Plant, task: Task, completionDate: Date) {
        taskHistoryDao.addTaskCompletionDate(
            RoomTaskCompletion.fromTask(
                task = task,
                completionDate = completionDate
            )
        )
    }

    override suspend fun isTaskCompletedForDate(plant: Plant, task: Task, date: Date): Boolean {
        return taskHistoryDao.isTaskCompletedForDate(
            taskId = task.id,
            startDay = date.atStartDay().time,
            endDay = date.atEndDay().time
        )
    }

    override suspend fun getTaskCompletionDates(plant: Plant, task: Task): List<Date> {
        TODO("Not implemented")
    }
}
