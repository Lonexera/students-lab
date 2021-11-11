package com.example.plantsapp.data.repository

import com.example.plantsapp.data.dao.RoomPlantsDao
import com.example.plantsapp.data.entity.RoomPlant
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

    // TODO delete date setting from here
    // TODO listen to todo above!!!
    override suspend fun deletePlant(plant: Plant) {
        plantsDao.delete(RoomPlant.from(plant, Date()))
    }
}
