package com.example.plantsapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.plantsapp.data.room.entity.RoomPlant
import kotlinx.coroutines.flow.Flow

@Deprecated("Room will no longer be used in app")
@Dao
interface RoomPlantsDao {

    @Query("SELECT * FROM plants")
    fun observeAll(): Flow<List<RoomPlant>>

    @Query("SELECT * FROM plants")
    suspend fun getAll(): List<RoomPlant>

    @Query("SELECT * FROM plants WHERE name = :plantName")
    suspend fun getByName(plantName: String): RoomPlant

    @Insert
    suspend fun insert(plant: RoomPlant)

    @Delete
    suspend fun delete(plant: RoomPlant)
}
