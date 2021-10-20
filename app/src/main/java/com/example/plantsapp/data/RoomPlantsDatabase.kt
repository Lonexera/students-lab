package com.example.plantsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [RoomPlant::class], version = 1)
abstract class RoomPlantsDatabase : RoomDatabase() {

    abstract fun plantDao(): RoomPlantsDao

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
