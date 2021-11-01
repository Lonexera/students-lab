package com.example.plantsapp.data

import com.example.plantsapp.domain.model.Plant
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
}
