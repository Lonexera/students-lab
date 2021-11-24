package com.example.plantsapp.data.repository

import com.example.plantsapp.data.dao.RoomTaskHistoryDao
import com.example.plantsapp.data.entity.RoomTaskCompletion
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantsapp.presentation.ui.utils.atEndDay
import com.example.plantsapp.presentation.ui.utils.atStartDay
import java.util.Date
import javax.inject.Inject

class RoomTasksHistoryRepository @Inject constructor(
    private val taskHistoryDao: RoomTaskHistoryDao
) : TasksHistoryRepository {

    override suspend fun createTaskCompletion(task: Task, completionDate: Date) {
        taskHistoryDao.addTaskCompletionDate(
            RoomTaskCompletion.fromTask(
                task = task,
                completionDate = completionDate.time
            )
        )
    }

    override suspend fun isTaskCompletedForDate(task: Task, date: Date): Boolean {
        return taskHistoryDao.isTaskCompletedForDate(
            taskId = task.id,
            startDay = date.atStartDay().time,
            endDay = date.atEndDay().time
        )
    }
}
