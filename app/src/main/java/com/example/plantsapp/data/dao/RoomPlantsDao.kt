package com.example.plantsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.plantsapp.data.entity.RoomPlant
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomPlantsDao {

    @Query("SELECT * FROM plants")
    fun getAll(): Flow<List<RoomPlant>>

    @Query("SELECT * FROM plants WHERE name = :plantName")
    suspend fun getByName(plantName: String): RoomPlant

    @Insert
    suspend fun insert(plant: RoomPlant)
}
