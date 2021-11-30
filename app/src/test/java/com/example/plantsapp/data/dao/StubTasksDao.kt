package com.example.plantsapp.data.dao

import com.example.plantsapp.data.entity.RoomTask

class StubTasksDao(
    private val roomTasksList: List<RoomTask>
) : RoomTasksDao {

    override suspend fun getTasksForPlantName(plantName: String): List<RoomTask> {
        return roomTasksList
    }

    override suspend fun update(taskId: Int, completionDate: Long) {
        TODO("Stub dao does not implement update method")
    }

    override suspend fun insert(tasks: List<RoomTask>) {
        TODO("Stub dao does not implement insert method")
    }
}
