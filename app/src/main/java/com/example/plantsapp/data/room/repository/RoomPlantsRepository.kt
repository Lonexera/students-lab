package com.example.plantsapp.data.room.repository

import com.example.plantsapp.data.room.dao.RoomPlantsDao
import com.example.plantsapp.data.room.entity.RoomPlant
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Deprecated("Room will no longer be used in app")
class RoomPlantsRepository @Inject constructor(
    private val plantsDao: RoomPlantsDao
) : PlantsRepository {

    override fun observePlants(): Flow<List<Plant>> {
        return plantsDao.observeAll()
            .map {
                it.map { roomPlant ->
                    roomPlant.toPlant()
                }
            }
    }

    override suspend fun fetchPlants(): List<Plant> {
        return plantsDao.getAll()
            .map { roomPlant ->
                roomPlant.toPlant()
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
