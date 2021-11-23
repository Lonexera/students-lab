package com.example.plantsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.plantsapp.data.entity.RoomPlantWithTasks
import com.example.plantsapp.data.entity.RoomTask

@Dao
interface RoomPlantWithTasksDao {

    @Transaction
    @Query("SELECT * FROM plants")
    suspend fun getPlantsWithTasks(): List<RoomPlantWithTasks>

    @Insert
    suspend fun insert(tasks: List<RoomTask>)
}
