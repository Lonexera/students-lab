package com.example.plantsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.plantsapp.data.entity.RoomPlantWithTasks
import com.example.plantsapp.data.entity.RoomTask
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomPlantWithTasksDao {

    @Transaction
    @Query("SELECT * FROM plants")
    fun getPlantsWithTasks(): Flow<List<RoomPlantWithTasks>>

    @Insert
    suspend fun insert(task: RoomTask)
}
