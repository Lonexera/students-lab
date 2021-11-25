package com.example.plantsapp.data

import androidx.room.Database
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
}
