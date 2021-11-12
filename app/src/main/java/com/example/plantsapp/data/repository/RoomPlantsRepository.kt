package com.example.plantsapp.data.repository

import com.example.plantsapp.data.dao.RoomPlantsDao
import com.example.plantsapp.data.entity.RoomPlant
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.PlantsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class RoomPlantsRepository @Inject constructor(
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

    override suspend fun getPlantByName(name: Plant.Name): Pair<Plant, List<Task>> {
        val plantWithTasks = plantsDao.getByName(name.value)
        return plantWithTasks.run {
            plant.toPlant() to tasks.map { it.toTask() }
        }
    }

    // TODO delete date setting from here
    override suspend fun deletePlant(plant: Plant) {
        plantsDao.delete(RoomPlant.from(plant, Date()))
    }
}
