package com.example.plantsapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomPlantsDao {

    @Query("SELECT * FROM plants")
    fun getAll(): Flow<List<RoomPlant>>

    @Query("SELECT * FROM plants WHERE name = :plantName")
    suspend fun getByName(plantName: String): RoomPlant

    @Query("SELECT COUNT() FROM plants WHERE name = :plantName")
    suspend fun countPlantsByName(plantName: String): Int

    @Insert
    suspend fun insert(plant: RoomPlant)
}
