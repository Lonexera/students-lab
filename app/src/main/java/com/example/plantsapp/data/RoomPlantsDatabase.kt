package com.example.plantsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantsapp.data.dao.RoomPlantWithTasksDao
import com.example.plantsapp.data.dao.RoomPlantsDao
import com.example.plantsapp.data.entity.RoomPlant
import com.example.plantsapp.data.entity.RoomTask

@Database(entities = [RoomPlant::class, RoomTask::class], version = 1)
abstract class RoomPlantsDatabase : RoomDatabase() {

    abstract fun plantDao(): RoomPlantsDao
    abstract fun plantsWithTasksDao(): RoomPlantWithTasksDao

    companion object {

        @Volatile
        private var INSTANCE: RoomPlantsDatabase? = null

        fun getInstance(context: Context): RoomPlantsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    RoomPlantsDatabase::class.java,
                    "plants_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
