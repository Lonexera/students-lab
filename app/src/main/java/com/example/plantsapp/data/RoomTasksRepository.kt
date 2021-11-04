package com.example.plantsapp.data

import androidx.core.net.toUri
import com.example.plantsapp.R
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import kotlin.reflect.KProperty1

class RoomTasksRepository(
    private val plantsDao: RoomPlantsDao
) : TasksRepository {

    override suspend fun getTasksForDate(date: Date): Flow<List<Task>> {
        return plantsDao.getAll()
            .map { plants ->
                listOf(
                    plants.getTasksForFrequency(
                        date,
                        RoomPlant::wateringFrequencyDays,
                        WATERING_ACTION,
                        WATERING_ICON
                    ),
                    plants.getTasksForFrequency(
                        date,
                        RoomPlant::sprayingFrequencyDays,
                        SPRAYING_ACTION,
                        SPRAYING_ICON
                    ),
                    plants.getTasksForFrequency(
                        date,
                        RoomPlant::looseningFrequencyDays,
                        LOOSENING_ACTION,
                        LOOSENING_ICON
                    ),
                ).flatten()
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

    private fun List<RoomPlant>.getTasksForFrequency(
        date: Date,
        frequencyProperty: KProperty1<RoomPlant, Int>,
        taskAction: Int,
        taskIcon: Int
    ): List<Task> {
        return this.filter {
            checkIfDateIsWithinInterval(
                date.time,
                it.creationDateMillis,
                frequencyProperty.get(it)
            )
        }
            .map {
                Task(
                    // TODO remove task action setting from repository
                    taskIcon,
                    taskAction,
                    it.name,
                    it.plantPicture?.toUri()
                )
            }
    }
}

private const val DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24
private const val WATERING_ACTION = R.string.title_watering_task
private const val SPRAYING_ACTION = R.string.title_spraying_task
private const val LOOSENING_ACTION = R.string.title_loosening_task
private const val WATERING_ICON = R.drawable.ic_watering
private const val SPRAYING_ICON = R.drawable.ic_spraying
private const val LOOSENING_ICON = R.drawable.ic_watering
