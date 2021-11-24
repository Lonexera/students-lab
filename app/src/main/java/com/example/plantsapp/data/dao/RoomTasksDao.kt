package com.example.plantsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.plantsapp.data.entity.RoomTask

@Dao
interface RoomTasksDao {

    @Transaction
    @Query("SELECT * FROM tasks WHERE tasks.plantName = :plantName")
    suspend fun getTasksForPlantName(plantName: String): List<RoomTask>

    @Insert
    suspend fun insert(tasks: List<RoomTask>)
}
