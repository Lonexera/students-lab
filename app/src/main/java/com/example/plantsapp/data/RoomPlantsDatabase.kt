package com.example.plantsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantsapp.data.dao.RoomTasksDao
import com.example.plantsapp.data.dao.RoomPlantsDao
import com.example.plantsapp.data.dao.RoomTaskHistoryDao
import com.example.plantsapp.data.entity.RoomPlant
import com.example.plantsapp.data.entity.RoomTask
import com.example.plantsapp.data.entity.RoomTaskCompletion

@Database(entities = [RoomPlant::class, RoomTask::class, RoomTaskCompletion::class], version = 1)
abstract class RoomPlantsDatabase : RoomDatabase() {

    abstract fun plantDao(): RoomPlantsDao
    abstract fun plantsWithTasksDao(): RoomTasksDao
    abstract fun taskHistoryDao(): RoomTaskHistoryDao

    companion object {

        @Volatile
        private var INSTANCE: RoomPlantsDatabase? = null

        // TODO move creation to Database Module (Hilt)
        fun getInstance(context: Context): RoomPlantsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    RoomPlantsDatabase::class.java,
                    "plants_database"
                )
                    .createFromAsset("database/plants_database.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
