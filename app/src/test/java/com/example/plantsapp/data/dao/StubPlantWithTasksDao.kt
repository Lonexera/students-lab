package com.example.plantsapp.data.dao

import com.example.plantsapp.data.entity.RoomPlant
import com.example.plantsapp.data.entity.RoomPlantWithTasks
import com.example.plantsapp.data.entity.RoomTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class StubPlantWithTasksDao(
    private val plants: List<RoomPlant>,
    private val tasks: List<RoomTask>
) : RoomPlantWithTasksDao {

    override fun getPlantsWithTasks(): Flow<List<RoomPlantWithTasks>> =
        flow {
            plants.map { plant ->
                RoomPlantWithTasks(
                    plant,
                    tasks.filter { it.plantName == plant.name }
                )
            }
        }

    override suspend fun insert(tasks: List<RoomTask>) {
        TODO("Stub dao does not implement insert method")
    }
}
