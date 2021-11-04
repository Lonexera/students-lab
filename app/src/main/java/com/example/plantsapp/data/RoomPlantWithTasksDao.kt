package com.example.plantsapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomPlantWithTasksDao {

    @Transaction
    @Query("SELECT * FROM plants")
    fun getPlantsWithTasks(): Flow<List<RoomPlantWithTasks>>

    @Insert
    suspend fun insert(task: RoomTask)
}
