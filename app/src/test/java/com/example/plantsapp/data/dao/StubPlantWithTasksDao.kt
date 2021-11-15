package com.example.plantsapp.data.dao

import com.example.plantsapp.data.entity.RoomPlantWithTasks
import com.example.plantsapp.data.entity.RoomTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class StubPlantWithTasksDao(
    private val plantWithTasks: List<RoomPlantWithTasks>
) : RoomPlantWithTasksDao {

    override fun getPlantsWithTasks(): Flow<List<RoomPlantWithTasks>> =
        flow {
            emit(plantWithTasks)
        }

    override suspend fun insert(tasks: List<RoomTask>) {
        TODO("Stub dao does not implement insert method")
    }
}
