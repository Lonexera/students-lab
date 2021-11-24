package com.example.plantsapp.data.repository

import com.example.plantsapp.data.dao.RoomPlantsDao
import com.example.plantsapp.data.entity.RoomPlant
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun addPlant(plant: Plant) {
        plantsDao.insert(RoomPlant.from(plant))
    }

    override suspend fun getPlantByName(name: Plant.Name): Plant {
        return plantsDao
            .getByName(name.value)
            .toPlant()
    }

    override suspend fun deletePlant(plant: Plant) {
        plantsDao.delete(RoomPlant.from(plant))
    }
}
