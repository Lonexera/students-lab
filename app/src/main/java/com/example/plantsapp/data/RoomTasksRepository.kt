package com.example.plantsapp.data

import androidx.core.net.toUri
import com.example.plantsapp.data.dao.RoomPlantWithTasksDao
import com.example.plantsapp.data.entity.RoomTask
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class RoomTasksRepository(
    private val plantsWithTasksDao: RoomPlantWithTasksDao
) : TasksRepository {

    override suspend fun addTasks(tasks: List<Task>) {
        tasks.forEach {
            plantsWithTasksDao.insert(
                RoomTask.from(it)
            )
        }
    }

    override suspend fun getTasksForDate(date: Date): Flow<List<Task>> {
        return plantsWithTasksDao.getPlantsWithTasks()
            .map { plantsWithTasks ->
                plantsWithTasks.map { plantWithTasks ->
                    plantWithTasks.tasks.filter {
                        checkIfDateIsWithinInterval(
                            date.time,
                            plantWithTasks.plant.creationDateMillis,
                            it.frequency
                        )
                    }
                        .map {
                            it.toTask(plantWithTasks.plant.plantPicture?.toUri())
                        }
                }
                    .flatten()
            }
    }

    private fun checkIfDateIsWithinInterval(
        dateMillis: Long,
        startDateMillis: Long,
        intervalDays: Int
    ): Boolean {
        return ((dateMillis - startDateMillis) / DAY_IN_MILLISECONDS) %
                intervalDays == 0L
    }
}

private const val DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24
