package com.example.plantsapp.data

import androidx.core.net.toUri
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class RoomTasksRepository(
    private val plantsDao: RoomPlantsDao
) : TasksRepository {

    override suspend fun getTasksForDate(date: Date): Flow<List<Task>> {
        return plantsDao.getAll()
            .map { plants ->
                plants.filter {
                    ((date.time - it.lastWatered) / DAY_IN_MILLISECONDS) %
                            it.wateringFrequencyDays == 0L
                }
                    .map {
                        Task(
                            // TODO remove task action setting from repository
                            WATERING_ACTION,
                            it.name,
                            it.plantPicture?.toUri()
                        )
                    }
            }
    }
}

private const val DAY_IN_MILLISECONDS = 86400000
private const val WATERING_ACTION = "Water"
