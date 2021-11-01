package com.example.plantsapp.data

import androidx.core.net.toUri
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.PlantsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class RoomPlantsRepository(
    private val plantsDao: RoomPlantsDao
) : PlantsRepository {

    override suspend fun fetchPlants(): Flow<List<Plant>> {
        return plantsDao.getAll().map {
            it.map { roomPlant ->
                roomPlant.toPlant()
            }
        }
    }

    // TODO remove date setting from repository
    override suspend fun addPlant(plant: Plant) {
        plantsDao.insert(RoomPlant.from(plant, Date()))
    }

    override suspend fun getPlantByName(name: Plant.Name): Plant {
        return plantsDao.getByName(name.value).toPlant()
    }

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
